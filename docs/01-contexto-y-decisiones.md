# 01 — Contexto y decisiones

## Descripción general del proyecto

**Colab** será una aplicación móvil orientada a facilitar la planificación y organización de proyectos académicos de software.

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
- **Backend:** Java, Spring Boot, API REST.
- **Base de datos:** PostgreSQL + JPA/Hibernate + Flyway.

---

## Decisiones de diseño (registro)

| # | Tema | Decisión |
|---|------|----------|
| 2.1 | IA | LLM real |
| 2.2 | Fechas | Backend calcula fechas; IA propone solo duraciones |
| 2.3 | Notificaciones | In-app (se consultan al abrir la app) |
| 2.4 | Autenticación | Email + contraseña (Google después) |
| 2.6 | IA + confirmación | IA una sola vez; edición manual posterior; confirmación individual |
| 2.7 | Habilidades | Catálogo (cerrado/semiabierto) |
| 2.8 | Tipos de proyecto | MVP: solo SOFTWARE con diseño extensible |
| 2.5 | Base de datos | PostgreSQL + JPA/Hibernate + Flyway |
| 2.5 | Roles/permisos | Creador (admin) + Integrante |
| 2.5 | Estados de tarea | 4 estados; BLOQUEADA calculada |
| 2.5 | Cálculo de fechas | Hacia atrás, días hábiles |
| 2.5 | Salida IA | JSON estricto validado |
| 2.8 | Información mínima | Objetivo + 1 RF + 1 tecnología + fecha + 2 integrantes |
| — | Idioma | Español |

### Detalle de las decisiones

#### Funcionamiento de la IA
- La IA se usa **una sola vez** para generar la propuesta inicial de planificación.
- Posteriormente, **todas las ediciones son manuales**; no se vuelve a llamar a la IA.
- La IA propone **duraciones relativas**, orden y dependencias; el **backend calcula las fechas concretas** (hacia atrás desde la fecha de entrega, en días hábiles).
- La salida de la IA debe ser un **JSON estructurado y estricto**, validado antes de mostrarse:
  - Dependencias sin ciclos.
  - Responsables existentes en el equipo.
  - Duraciones mayores a 0.

#### Flujo de generación y aprobación
1. El equipo registra la información mínima del proyecto y los perfiles de habilidades.
2. El sistema valida la "información suficiente".
3. Se llama a la IA **una sola vez** → genera la propuesta completa.
4. La salida se valida y se guarda como planificación en estado **`PROPUESTA`**.
5. Fase de **revisión/edición manual** (sin IA).
6. Fase de **confirmación individual**: cada integrante acepta sus propias tareas ("Acepto mis tareas") o solicita cambios.
7. Cuando **todos** confirman → la planificación pasa a estado **`ACTIVA`**.

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

*Cada tarea tiene como máximo un responsable (el integrante asignado).*

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
- Al menos 2 integrantes con perfil de habilidades completado.
