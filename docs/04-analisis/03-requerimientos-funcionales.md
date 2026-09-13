# 03 — Requerimientos funcionales

## 10. Requerimientos funcionales

Los requerimientos funcionales describen las acciones que el sistema debe realizar. Se identifican con el formato `RF-XX` e indican, entre paréntesis, el actor principal involucrado.

### 10.1 Autenticación y gestión de usuarios

- **RF-01 — Registro de usuario.** El sistema debe permitir a una persona crear una cuenta proporcionando correo electrónico y contraseña. *(Visitante)*
- **RF-02 — Inicio de sesión.** El sistema debe autenticar a un usuario registrado mediante correo electrónico y contraseña. *(Usuario)*
- **RF-03 — Cierre de sesión.** El sistema debe permitir al usuario cerrar su sesión. *(Usuario)*
- **RF-04 — Gestión del perfil de habilidades.** El sistema debe permitir al usuario registrar y editar sus habilidades y conocimientos mediante un catálogo. *(Usuario)*

### 10.2 Gestión de proyectos

- **RF-05 — Crear proyecto.** El sistema debe permitir a un usuario crear un proyecto de software y convertirse en su Creador. *(Usuario)*
- **RF-06 — Registrar información del proyecto.** El sistema debe permitir capturar la información del proyecto: problemática, descripción, objetivo general, objetivos específicos, alcance, requerimientos funcionales, requerimientos no funcionales, casos de uso, tecnologías, restricciones y fecha de entrega. *(Creador)*
- **RF-07 — Editar información del proyecto.** El sistema debe permitir al Creador modificar la información del proyecto. *(Creador)*
- **RF-08 — Visualizar proyecto.** El sistema debe permitir a los integrantes consultar la información del proyecto. *(Creador, Integrante)*
- **RF-09 — Eliminar proyecto.** El sistema debe permitir al Creador eliminar el proyecto. *(Creador)*

### 10.3 Gestión del equipo

- **RF-10 — Invitar integrantes.** El sistema debe permitir al Creador agregar o invitar integrantes al proyecto. *(Creador)*
- **RF-11 — Aceptar invitación.** El sistema debe permitir a un usuario aceptar una invitación para unirse a un proyecto. *(Usuario invitado)*
- **RF-12 — Eliminar integrante.** El sistema debe permitir al Creador retirar a un integrante del proyecto. *(Creador)*
- **RF-13 — Visualizar equipo.** El sistema debe mostrar los integrantes del proyecto y sus habilidades. *(Creador, Integrante)*

### 10.4 Generación de la planificación con IA

- **RF-14 — Validar información suficiente.** El sistema debe verificar que existan los datos mínimos (objetivo general, al menos un requerimiento funcional, una tecnología, fecha de entrega y al menos dos integrantes con perfil, contando al Creador) antes de generar la planificación. *(Sistema)*
- **RF-15 — Generar planificación con IA.** El sistema debe llamar a un LLM (una sola vez) para generar una propuesta de planificación en JSON a partir de la información del proyecto y del equipo. La propuesta debe incluir las etapas, las tareas, los responsables sugeridos (según las habilidades), las prioridades, las dependencias, el orden y las duraciones. *(Sistema/IA)* — El LLM será el servicio institucional de TECSUP "La BestIA" (ver [08 — Integración de la IA](08-integracion-ia.md)).
- **RF-16 — Validar salida de la IA.** El sistema debe validar la salida de la IA: dependencias sin ciclos, responsables existentes y duraciones positivas. *(Sistema)*
- **RF-17 — Calcular fechas estimadas.** El sistema debe calcular las fechas de inicio y fin de cada tarea a partir de las duraciones y el orden propuestos, hacia atrás desde la fecha de entrega. *(Sistema)*

### 10.5 Revisión, edición y aprobación de la planificación

- **RF-18 — Visualizar propuesta.** El sistema debe mostrar la planificación propuesta (etapas, tareas, responsables, prioridades, dependencias, orden y fechas). *(Creador, Integrante)*
- **RF-19 — Editar propuesta.** El sistema debe permitir al Creador editar manualmente la planificación propuesta (sin volver a llamar a la IA). *(Creador)*
- **RF-20 — Confirmar tareas.** El sistema debe permitir a cada integrante (incluido el Creador, si tiene tareas asignadas) aceptar sus tareas asignadas. *(Integrante, Creador)*
- **RF-21 — Solicitar cambios.** El sistema debe permitir a un integrante (incluido el Creador, si tiene tareas asignadas) solicitar cambios sobre sus tareas, indicando un comentario. *(Integrante, Creador)*
- **RF-22 — Activar planificación.** El sistema debe activar la planificación cuando todos los integrantes hayan confirmado sus tareas. *(Sistema)*

### 10.6 Gestión de tareas

> **Nota:** Las operaciones de esta sección son **manuales** y complementan la generación automática de la IA (RF-15). La IA propone inicialmente las tareas y sus responsables; el Creador puede ajustarlas manualmente durante la revisión de la propuesta.

- **RF-23 — Crear tarea (manual).** El sistema debe permitir al Creador crear tareas manualmente para ajustar la propuesta generada por la IA, con nombre, descripción, responsable, estado, prioridad, fechas y dependencias. *(Creador)*
- **RF-24 — Editar tarea.** El sistema debe permitir modificar los datos de una tarea. *(Creador)*
- **RF-25 — Eliminar tarea.** El sistema debe permitir eliminar una tarea. *(Creador)*
- **RF-26 — Reasignar responsable (manual).** El sistema debe permitir al Creador modificar manualmente el responsable de una tarea propuesto por la IA. *(Creador)*
- **RF-27 — Definir dependencias.** El sistema debe permitir establecer dependencias entre tareas (finish-to-start), sin permitir ciclos. *(Creador)*
- **RF-28 — Cambiar estado de tarea.** El sistema debe permitir al responsable (o al Creador) cambiar el estado de una tarea según las transiciones permitidas. *(Integrante, Creador)*
- **RF-29 — Bloquear tarea automáticamente.** El sistema debe mostrar como Bloqueada una tarea que tenga al menos una dependencia incompleta. *(Sistema)*
- **RF-30 — Desbloquear tarea automáticamente.** El sistema debe pasar a Pendiente una tarea bloqueada cuando se completen todas sus dependencias. *(Sistema)*
- **RF-31 — Vista "Mis tareas".** El sistema debe permitir a cada integrante visualizar las tareas que le fueron asignadas. *(Integrante)*
- **RF-32 — Vista de todas las tareas.** El sistema debe mostrar todas las tareas del proyecto con su estado y responsable. *(Creador, Integrante)*

### 10.7 Notificaciones

- **RF-33 — Generar notificaciones.** El sistema debe generar notificaciones in-app ante eventos como asignación de tarea, cambio de estado, habilitación de tarea bloqueada, cambio de fecha o responsable y cambios en la planificación. *(Sistema)*
- **RF-34 — Visualizar notificaciones.** El sistema debe permitir a los integrantes ver sus notificaciones al abrir la app. *(Integrante)*
- **RF-35 — Marcar notificación como leída.** El sistema debe permitir marcar una notificación como leída. *(Integrante)*
