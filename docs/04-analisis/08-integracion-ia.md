# 08 — Integración de la IA (TECSUP — "La BestIA")

> Documento técnico que especifica el servicio de inteligencia artificial provisto por el instituto **TECSUP** y cómo se integra en **Colab** para generar la propuesta de planificación (ver RF-15, RF-16, RNF-07 y CU-08).

## 1. Resumen

Colab requiere un modelo de lenguaje (LLM) para convertir la información del proyecto y las habilidades del equipo en una propuesta de planificación en **JSON estructurado y validado**. Para ello se utilizará el servicio institucional de IA de TECSUP, denominado **"La BestIA"**, publicado en el repositorio [`jgomezz/tdgpt_ia`](https://github.com/jgomezz/tdgpt_ia).

"La BestIA" es una instancia de **OpenWebUI** que expone una **API REST compatible con OpenAI** (endpoint `/api/chat/completions`). Al ser una API HTTP/JSON estándar, puede consumirse desde cualquier lenguaje; el backend de Colab (Java + Spring Boot) la invocará directamente mediante un cliente HTTP.

## 2. Acceso y requisitos

| Elemento | Valor |
|---|---|
| URL base | `http://192.168.17.11:3000` |
| Autenticación | Cabecera `Authorization: Bearer <TOKEN>` |
| Red requerida | Red cableada de los laboratorios de TD o red WiFi **"Comunidad Innovadores"** |

> ⚠️ **Restricción de red.** El servicio solo es alcanzable **dentro de la red del instituto**. Desde una red externa no es posible acceder a `192.168.17.11`.

### 2.1 Obtener el TOKEN

1. Ingresar a `http://192.168.17.11:3000` desde la red del instituto.
2. Ir a **Ajustes**.
3. **Generar el TOKEN**.

El TOKEN se guarda en el backend como variable de entorno (ver sección 8), nunca en el código ni en la app móvil.

## 3. Modelos disponibles

| Modelo | Notas |
|---|---|
| `Qwen/Qwen3.6-35B-A3B-FP8` | Modelo indicado por defecto en el repositorio. |
| `google/gemma-4-26B-A4B-it` | Alternativa. |
| `mistralai/Ministral-3-14B-Instruct-2512` | Alternativa. |

Para consultar la lista actualizada de modelos: `GET http://192.168.17.11:3000/api/v1/models`.

## 4. Endpoints

### 4.1 Listar modelos

- **Método:** `GET`
- **URL:** `http://192.168.17.11:3000/api/v1/models`
- **Headers:**
  - `Authorization: Bearer <TOKEN>`
  - `Content-Type: application/json`

La respuesta contiene la lista de modelos en el campo `data`.

### 4.2 Generación de texto (chat completions)

- **Método:** `POST`
- **URL:** `http://192.168.17.11:3000/api/chat/completions`
- **Headers:**
  - `Authorization: Bearer <TOKEN>`
  - `Content-Type: application/json`

## 5. Formato de petición y respuesta

### 5.1 Petición (sin streaming)

```json
{
  "model": "Qwen/Qwen3.6-35B-A3B-FP8",
  "messages": [
    { "role": "system", "content": "Eres un asistente que planifica proyectos de software académicos." },
    { "role": "user", "content": "..." }
  ],
  "stream": false
}
```

### 5.2 Respuesta (sin streaming)

Formato compatible con OpenAI; el texto generado está en `choices[0].message.content`:

```json
{
  "choices": [
    {
      "message": { "role": "assistant", "content": "..." }
    }
  ]
}
```

### 5.3 Streaming (`stream: true`)

Con `"stream": true` la respuesta se entrega mediante eventos SSE (`data: ...`, finalizando con `data: [DONE]`). El contenido incremental se lee de `choices[0].delta.content`.

> Para la generación de la planificación en Colab se recomienda **`stream: false`** (una sola respuesta completa, más simple de parsear y validar). El streaming queda como opción si se desea mostrar progreso incremental (RNF-02).

### 5.4 Ejemplo con curl (para probar)

```bash
curl -s -X POST "http://192.168.17.11:3000/api/chat/completions" \
  -H "Authorization: Bearer COPIAR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "model": "Qwen/Qwen3.6-35B-A3B-FP8",
    "messages": [
      { "role": "user", "content": "Dame un programa en Python que imprima Hola, mundo!" }
    ],
    "stream": false
  }' | jq -r '.choices[0].message.content'
```

## 6. Integración en Colab (arquitectura)

- La llamada a "La BestIA" se realiza **siempre desde el backend** (Spring Boot), nunca desde los clientes (app móvil o web). Esto garantiza el RNF-07 (el TOKEN no se expone) y centraliza la validación.
- El flujo es el definido en CU-08 / RF-15: el Creador solicita "Generar planificación" → el backend valida la información mínima (RN-18) → invoca a la IA **una sola vez** → valida la salida (RN-19) → calcula las fechas (RN-20) → guarda la propuesta en estado `PROPUESTA`.
- El backend se comunicará con el endpoint `/api/chat/completions` mediante un cliente HTTP de Spring (por ejemplo, `RestClient` o `WebClient`), enviando un arreglo `messages` con:
  1. Un mensaje `system` que define el rol y las reglas de salida (responder **solo con JSON**, sin texto adicional).
  2. Un mensaje `user` con la información del proyecto y del equipo.

## 7. Diseño del prompt y contrato de salida JSON

### 7.1 Prompt

El prompt debe instruir a la IA a responder **únicamente con JSON válido** (sin markdown, sin texto explicativo), con la estructura de planificación descrita a continuación.

### 7.2 Contrato JSON esperado

```json
{
  "etapas": [
    {
      "nombre": "Análisis",
      "orden": 1,
      "tareas": [
        {
          "id": "t1",
          "nombre": "Definir requerimientos funcionales",
          "descripcion": "Redactar los RF del proyecto.",
          "responsable": "correo.del.integrante@ejemplo.com",
          "habilidadesRequeridas": ["Análisis", "Requerimientos"],
          "prioridad": "ALTA",
          "duracionDias": 2,
          "dependencias": []
        },
        {
          "id": "t2",
          "nombre": "Validar requerimientos",
          "descripcion": "Revisar los RF con el equipo.",
          "responsable": "otro.integrante@ejemplo.com",
          "habilidadesRequeridas": ["Análisis"],
          "prioridad": "ALTA",
          "duracionDias": 1,
          "dependencias": ["t1"]
        }
      ]
    }
  ]
}
```

**Reglas del contrato:**

- `id` es un identificador breve y **único** de cada tarea dentro de la respuesta, usado para referenciar las dependencias.
- `responsable` debe ser un integrante **existente** en el equipo (identificado por su correo).
- `dependencias` referencia los `id` de las tareas predecesoras (finish-to-start).
- `duracionDias` debe ser un entero **mayor que 0**.
- `prioridad` toma valores `ALTA`, `MEDIA` o `BAJA`.

> La IA propone **duraciones, orden y dependencias**; las **fechas concretas** las calcula el backend (RN-20), hacia atrás desde la fecha de entrega y contando **días naturales** del calendario.

## 8. Seguridad (RNF-07)

- El TOKEN de "La BestIA" se almacena como **variable de entorno del backend** (por ejemplo, `USER_API_KEY`), junto con `OPENWEBUI_URL` y `MODEL`.
- Nunca se incluye en el código fuente, en el repositorio ni en la app móvil.
- El backend es el único componente que conoce el TOKEN y el único que realiza las llamadas a la IA.

## 9. Manejo de errores y reintento (RNF-12)

- Si la IA responde con un código distinto de 200, o devuelve un JSON inválido, el backend debe **notificar el error** al Creador y permitir **reintentar**, sin perder la información del proyecto.
- La salida se valida antes de mostrarse (RF-16 / RN-19): dependencias sin ciclos, responsables existentes y duraciones positivas. Si falla la validación, se descarta y se puede reintentar.

## 10. Rendimiento (RNF-02)

- La generación con IA puede tardar más que una operación CRUD; por ello se ejecuta de forma **asíncrona** y se muestra un estado de progreso, sin bloquear la interfaz móvil.

## Referencias

- Repositorio oficial: <https://github.com/jgomezz/tdgpt_ia>
- Documento de instalación del repositorio: `INSTALL.md`
- Ejemplos de uso en el repositorio: `src/list_models_ia.py` y `src/test_model_api_rest.py`

