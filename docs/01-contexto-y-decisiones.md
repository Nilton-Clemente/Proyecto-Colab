# 01 — Contexto y decisiones

## Descripción general del proyecto

**Colab** será una aplicación **móvil y web** orientada a facilitar la planificación y organización de proyectos académicos de software.

La plataforma utilizará inteligencia artificial para analizar la información de un proyecto y las habilidades de sus integrantes, con el objetivo de generar una propuesta de planificación personalizada.

La aplicación buscará ayudar a los equipos de estudiantes a responder preguntas como:

- ¿Qué actividades debemos realizar para desarrollar nuestro proyecto?
- ¿En qué orden debemos realizar las actividades?
- ¿Qué integrante podría ser responsable de cada tarea según sus habilidades?
- ¿Qué tareas dependen de otras?
- ¿Qué actividades pueden realizarse simultáneamente?
- ¿Cómo podemos organizarnos hasta la fecha de entrega?

### Alcance inicial

Por el momento, el sistema estará orientado únicamente a **proyectos de software académicos**. La información utilizada para analizar los proyectos estará relacionada con elementos como:

- Problemática.
- Descripción del proyecto.
- Objetivo general.
- Objetivos específicos.
- Alcance.
- Requerimientos funcionales.
- Requerimientos no funcionales.
- Casos de uso o historias de usuario.
- Tecnologías seleccionadas.
- Restricciones.
- Fecha de entrega.

### Tecnologías iniciales

- **Aplicación móvil:** Android, Kotlin, Jetpack Compose.
- **Aplicación web (usuario):** React, consumiendo el mismo backend de Spring Boot.
- **Plataforma de administración:** React (frontend) + Django (backend).
- **Backend (usuario):** Java, Spring Boot, API REST.
- **Base de datos:** PostgreSQL compartido por ambos backends, con **esquemas separados**: `usuario` (Spring Boot + JPA/Hibernate + Flyway) y `admin` (Django + migraciones de Django).

---

## Decisiones de diseño (registro)

| # | Tema | Decisión |
|---|------|----------|
| 2.1 | IA | TECSUP — "La BestIA" (OpenWebUI, API compatible con OpenAI) |
| 2.2 | Fechas | Backend calcula fechas; IA propone solo duraciones |
| 2.3 | Notificaciones | In-app (se consultan al abrir la app) |
| 2.4 | Autenticación | Email + contraseña (Google después) |
| 2.5 | IA + confirmación | IA una sola vez; edición manual posterior; confirmación individual |
| 2.6 | Habilidades | Catálogo (cerrado/semiabierto) |
| 2.7 | Tipos de proyecto | MVP: solo SOFTWARE con diseño extensible |
| 2.8 | Base de datos | PostgreSQL compartido; esquemas separados: `usuario` (JPA/Flyway) y `admin` (Django) |
| 2.9 | Roles/permisos | Creador (integrante con permisos de administración) + Integrante |
| 2.10 | Estados de tarea | 4 estados; BLOQUEADA calculada |
| 2.11 | Cálculo de fechas | Hacia atrás, días naturales (calendario) |
| 2.12 | Salida IA | JSON estricto validado |
| 2.13 | Información mínima | Objetivo + 1 RF + 1 tecnología + fecha + 2 integrantes (incluido el Creador) |
| 2.14 | Interfaces y administración | Usuario: móvil (Kotlin/Compose) + web (React); Administración: React + Django |
| 2.15 | Idioma | Español |

### Detalle de las decisiones

#### Funcionamiento de la IA
- La IA se usa **una sola vez** para generar la propuesta inicial de planificación.
- Posteriormente, **todas las ediciones son manuales**; no se vuelve a llamar a la IA.
- La IA propone **duraciones relativas**, orden y dependencias; el **backend calcula las fechas concretas** (hacia atrás desde la fecha de entrega, contando **días naturales** del calendario: en el MVP todos los días cuentan, sin descontar fines de semana ni feriados).
- La salida de la IA debe ser un **JSON estructurado y estricto**, validado antes de mostrarse:
  - Dependencias sin ciclos.
  - Responsables existentes en el equipo.
  - Duraciones mayores a 0.

> **Proveedor de IA:** el LLM concreto será el servicio institucional de TECSUP **"La BestIA"** (OpenWebUI), accesible solo desde la red del instituto. Los detalles técnicos (endpoint, autenticación, modelos y contrato JSON) están en [08 — Integración de la IA](04-analisis/08-integracion-ia.md).

#### Flujo de generación y aprobación
1. El equipo registra la información mínima del proyecto y los perfiles de habilidades.
2. El sistema valida la "información suficiente".
3. Se llama a la IA **una sola vez** → genera la propuesta completa.
4. La salida se valida y se guarda como planificación en estado **`PROPUESTA`**.
5. Fase de **revisión/edición manual** (sin IA).
6. Fase de **confirmación individual**: cada integrante (incluido el Creador, si tiene tareas asignadas) acepta sus propias tareas ("Acepto mis tareas") o solicita cambios.
7. Cuando **todos** (incluido el Creador) confirman → la planificación pasa a estado **`ACTIVA`**.

#### Estados de la planificación
- **`BORRADOR`**: información registrándose, aún no hay plan.
- **`PROPUESTA`**: plan generado, en revisión/edición.
- **`ACTIVA`**: todos confirmaron, tareas operativas.
- *(Futuro)* **`FINALIZADA`**.

#### Roles y permisos (MVP)

| Acción | CREADOR | INTEGRANTE |
|---|---|---|
| Editar información del proyecto | ✅ | ❌ |
| Invitar / eliminar integrantes | ✅ | ❌ |
| Generar planificación (IA) | ✅ | ❌ |
| Editar la propuesta (plan) | ✅ | ❌ (solo visualiza) |
| Aceptar / solicitar cambios de SUS tareas | ✅ | ✅ |
| Cambiar estado de SUS tareas | ✅ | ✅ |
| Cambiar estado de CUALQUIER tarea | ✅ | ❌ |
| Ver todo el proyecto | ✅ | ✅ |

*El **Creador** es, a su vez, un **integrante** del equipo (con permisos de administración): el integrante que crea un proyecto asume, además, el rol de Creador.*

*Cada tarea tiene como máximo un responsable, que puede ser cualquier integrante del equipo (incluido el Creador).*

#### Estados de tarea y transiciones

Estados: `PENDIENTE`, `EN_PROGRESO`, `BLOQUEADA`, `COMPLETADA`.

- **`BLOQUEADA`** es un estado **calculado** (derivado de dependencias incompletas), no se asigna a mano.

Transiciones manuales (por el responsable o el creador):
- `PENDIENTE` → `EN_PROGRESO` (solo si no está bloqueada).
- `EN_PROGRESO` → `COMPLETADA`.
- `EN_PROGRESO` → `PENDIENTE` (deshacer).
- `COMPLETADA` es **terminal** (no reversible en MVP).

Reglas automáticas:
- Una tarea con ≥1 dependencia incompleta se muestra como `BLOQUEADA`.
- Al completar la última dependencia, se desbloquea automáticamente a `PENDIENTE` (con notificación).

#### Información mínima para "Generar planificación" (tipo SOFTWARE)
- Objetivo general.
- Al menos 1 requerimiento funcional.
- Al menos 1 tecnología.
- Fecha de entrega.
- Al menos 2 integrantes con perfil de habilidades completado (el Creador cuenta como un integrante más).

#### Plataforma web y de administración

- Además de la aplicación móvil, Colab contará con una **aplicación web de usuario** (React) que consume el mismo backend de Spring Boot.
- Existirá una **plataforma de administración** (frontend React + backend Django) para las operaciones administrativas del sistema.
- Ambos backends (Spring Boot y Django) compartirán la misma instancia de PostgreSQL, con esquemas separados (`usuario` y `admin`).
- La plataforma de administración **forma parte del entregable MVP**; su alcance funcional detallado se define en el [05 — Diseño](05-diseno.md) y [06 — Gestión](06-gestion.md).
