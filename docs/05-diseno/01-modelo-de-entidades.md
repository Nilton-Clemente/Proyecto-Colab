# 01 — Modelo de entidades

## 15. Modelo inicial de entidades

Este modelo se deriva del análisis ([04 — Análisis](../04-analisis/README.md)). Salvo que se indique lo contrario, todas las entidades pertenecen al **esquema `usuario`** (Spring Boot). Las entidades del **esquema `admin`** (Django) quedan pendientes de definición.

Leyenda: **PK** = clave primaria · **FK** = clave foránea · **U** = valor único · `*` = obligatorio.

---

### 15.1 Usuario
Estudiante registrado. No es un "rol de proyecto": el rol se guarda en `Integrante`.

- `id` (PK)
- `email` (U, `*`) — usado para iniciar sesión y para invitar
- `contrasenaHash` (`*`) — nunca en texto plano (RNF-04)
- `nombre` (`*`)
- `fechaRegistro`

### 15.2 Habilidad
Catálogo de habilidades y conocimientos (cerrado/semiabierto — decisión 2.6).

- `id` (PK)
- `nombre` (U, `*`) — p. ej. "Kotlin", "Django", "Análisis"
- `area` — categoría (Backend, Frontend, BD, IA, Gestión…)

### 15.3 UsuarioHabilidad
Perfil de habilidades del usuario (N:M entre Usuario y Habilidad).

- `usuarioId` (FK → Usuario, `*`)
- `habilidadId` (FK → Habilidad, `*`)
- `nivel` — opcional (Básico / Intermedio / Avanzado)

### 15.4 Proyecto
Proyecto académico de software.

- `id` (PK)
- `nombre` (`*`)
- `descripcion`
- `problematica`
- `objetivoGeneral`
- `objetivosEspecificos` — lista / texto
- `alcance`
- `restricciones`
- `fechaEntrega` — date
- `fechaCreacion`
- `tipo` — siempre `SOFTWARE` en el MVP

### 15.5 Tecnologia
Catálogo de tecnologías seleccionables.

- `id` (PK)
- `nombre` (U, `*`)

### 15.6 ProyectoTecnologia
Relación N:M entre Proyecto y Tecnologia.

- `proyectoId` (FK → Proyecto, `*`)
- `tecnologiaId` (FK → Tecnologia, `*`)

### 15.7 RequerimientoFuncional (RF)
Requerimiento funcional del proyecto (1:N con Proyecto).

- `id` (PK)
- `proyectoId` (FK → Proyecto, `*`)
- `codigo` — p. ej. "RF-01"
- `descripcion`

### 15.8 RequerimientoNoFuncional (RNF)
Requerimiento no funcional del proyecto (1:N con Proyecto).

- `id` (PK)
- `proyectoId` (FK → Proyecto, `*`)
- `codigo` — p. ej. "RNF-01"
- `categoria` — rendimiento, seguridad, usabilidad…
- `descripcion`

### 15.9 CasoUso (CU)
Caso de uso registrado del proyecto (1:N con Proyecto).

- `id` (PK)
- `proyectoId` (FK → Proyecto, `*`)
- `codigo` — p. ej. "CU-01"
- `nombre`
- `descripcion`

### 15.10 Integrante
Pertenencia de un Usuario a un Proyecto con rol (N:M con atributos).

- `id` (PK) — o clave compuesta (`usuarioId` + `proyectoId`)
- `usuarioId` (FK → Usuario, `*`)
- `proyectoId` (FK → Proyecto, `*`)
- `rol` (`*`) — `CREADOR` / `INTEGRANTE`
- `estado` (`*`) — `PENDIENTE` (invitado) / `ACTIVO` (aceptó)
- `fechaUnion`

> El **Creador es también un integrante** del equipo (con rol `CREADOR`). Crear un proyecto crea su registro `Integrante` con rol `CREADOR`.

### 15.11 Planificacion
Propuesta de planificación generada con IA (una sola vez, decisión 2.5).

- `id` (PK)
- `proyectoId` (FK → Proyecto, `*`)
- `estado` (`*`) — `BORRADOR` / `PROPUESTA` / `ACTIVA` / `FINALIZADA`
- `fechaGeneracion`

### 15.12 Etapa
Fase dentro de la planificación.

- `id` (PK)
- `planificacionId` (FK → Planificacion, `*`)
- `nombre` (`*`)
- `orden`

### 15.13 Tarea
Unidad de trabajo dentro de una etapa.

- `id` (PK)
- `etapaId` (FK → Etapa, `*`)
- `nombre` (`*`)
- `descripcion`
- `responsableId` (FK → Integrante) — máximo 1 responsable; puede ser el Creador; nulo si aún no se asigna
- `prioridad` (`*`) — `ALTA` / `MEDIA` / `BAJA`
- `estado` (`*`) — `PENDIENTE` / `EN_PROGRESO` / `COMPLETADA` (`BLOQUEADA` se calcula y no se guarda)
- `duracionDias` (`*`, > 0)
- `fechaInicio` / `fechaFin` — calculadas por el backend (días naturales, RN-20)
- `orden`

### 15.14 DependenciaTarea
Dependencia "finish-to-start" entre tareas (auto-relación N:M de Tarea).

- `tareaId` (FK → Tarea, `*`) — tarea dependiente
- `tareaPredecesoraId` (FK → Tarea, `*`) — tarea predecesora

> Sin ciclos (RN-11). El estado `BLOQUEADA` se deriva de esta tabla.

### 15.15 Confirmacion
Confirmación individual de cada integrante sobre la propuesta.

- `id` (PK)
- `planificacionId` (FK → Planificacion, `*`)
- `integranteId` (FK → Integrante, `*`) — incluido el Creador si tiene tareas
- `estado` (`*`) — `PENDIENTE` / `ACEPTADA` / `CAMBIOS_SOLICITADOS`
- `comentario`
- `fecha`

### 15.16 Notificacion
Notificación in-app a un usuario.

- `id` (PK)
- `usuarioId` (FK → Usuario, `*`) — destinatario
- `tipoEvento` (`*`) — asignación, cambio de estado, desbloqueo, cambio de fecha/responsable, planificación
- `mensaje` (`*`)
- `leida` (booleano, `*`)
- `fechaCreacion`

### 15.17 Esquema `admin` (pendiente)
Las entidades del área de Administración (Django) se definirán al detallar sus funciones (diferido por decisión del equipo). Por ahora solo se reserva el esquema `admin`.
