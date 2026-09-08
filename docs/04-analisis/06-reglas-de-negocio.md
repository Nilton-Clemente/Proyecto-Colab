# 06 — Reglas de negocio

## 13. Reglas de negocio

Las reglas de negocio formalizan las restricciones y comportamientos del dominio. Se identifican con el formato `RN-XX`.

### 13.1 Estados de la planificación

- **RN-01 — Ciclo de vida.** La planificación pasa por los estados BORRADOR → PROPUESTA → ACTIVA → (opcional) FINALIZADA, en ese orden.
- **RN-02 — Generación única.** La planificación se genera mediante IA una sola vez; no se vuelve a llamar a la IA después de la generación inicial.
- **RN-03 — Activación por confirmación total.** La planificación pasa de PROPUESTA a ACTIVA solo cuando todos los integrantes han confirmado sus tareas.
- **RN-04 — Solicitud de cambios.** Si un integrante solicita cambios, la planificación vuelve a edición y se requiere una nueva confirmación de todos.

### 13.2 Estados de las tareas

- **RN-05 — Estados posibles.** Los estados de una tarea son PENDIENTE, EN_PROGRESO, BLOQUEADA y COMPLETADA.
- **RN-06 — Estado calculado.** El estado BLOQUEADA no se asigna manualmente; se deriva de las dependencias incompletas.
- **RN-07 — Transiciones permitidas.** Las transiciones manuales permitidas son: PENDIENTE → EN_PROGRESO, EN_PROGRESO → COMPLETADA y EN_PROGRESO → PENDIENTE.
- **RN-08 — Tarea terminal.** El estado COMPLETADA es terminal y no reversible en el MVP.

### 13.3 Dependencias entre tareas

- **RN-09 — Bloqueo por dependencias.** Una tarea queda bloqueada si tiene al menos una dependencia directa incompleta.
- **RN-10 — Desbloqueo automático.** Al completarse todas las dependencias de una tarea bloqueada, esta pasa automáticamente a PENDIENTE.
- **RN-11 — Sin ciclos.** No se permite crear ciclos en las dependencias entre tareas.
- **RN-12 — Tipo de dependencia.** Las dependencias son de tipo "finish-to-start": una tarea inicia cuando su predecesora termina.

### 13.4 Roles y permisos

- **RN-13 — Rol de Creador.** El usuario que crea un proyecto es su Creador y tiene permisos de administración.
- **RN-14 — Permisos del Creador.** Solo el Creador puede editar la información del proyecto, invitar o eliminar integrantes, generar la planificación, editar la propuesta y cambiar el estado de cualquier tarea.
- **RN-15 — Permisos del Integrante.** El Integrante solo puede confirmar sus tareas y cambiar el estado de las tareas que tiene asignadas.
- **RN-16 — Responsable único.** Cada tarea tiene como máximo un responsable (integrante).
- **RN-17 — Aceptación de invitación.** Un usuario se convierte en Integrante de un proyecto solo después de aceptar la invitación enviada por el Creador.

### 13.5 Generación de la planificación

- **RN-18 — Información mínima.** Para generar la planificación, el proyecto debe tener: objetivo general, al menos un requerimiento funcional, una tecnología, fecha de entrega y al menos dos integrantes con perfil de habilidades.
- **RN-19 — Validación de la salida.** La salida de la IA debe validarse: dependencias sin ciclos, responsables existentes y duraciones positivas.
- **RN-20 — Cálculo de fechas.** Las fechas de las tareas las calcula el backend (hacia atrás desde la fecha de entrega, en días hábiles), a partir de las duraciones propuestas por la IA.
- **RN-21 — Advertencia de fecha.** Si la planificación excede la fecha de entrega, se muestra una advertencia de riesgo, sin bloquear la generación.

### 13.6 Notificaciones

- **RN-22 — Eventos de notificación.** El sistema genera notificaciones ante: asignación de tarea, cambio de estado, habilitación de tarea bloqueada, cambio de fecha o responsable y cambios en la planificación.
