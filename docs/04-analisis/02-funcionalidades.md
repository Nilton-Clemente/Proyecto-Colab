# 02 — Funcionalidades

## 9. Funcionalidades principales

Las funcionalidades principales del sistema se agrupan en los siguientes módulos:

### 9.1 Autenticación y gestión de usuarios
- **Registro de usuario:** creación de una cuenta con correo electrónico y contraseña.
- **Inicio de sesión:** autenticación de usuarios registrados.
- **Perfil de usuario:** registro y edición de habilidades y conocimientos mediante un catálogo.

### 9.2 Gestión de proyectos
- **Crear proyecto:** un estudiante crea un proyecto de software y se convierte en su Creador.
- **Registrar información del proyecto:** captura de la información estructurada (problemática, objetivos, alcance, requerimientos, tecnologías, restricciones y fecha de entrega).
- **Editar y visualizar proyecto:** consulta y modificación de los datos del proyecto.

### 9.3 Gestión del equipo
- **Invitar integrantes:** el Creador agrega o invita a los demás miembros del equipo.
- **Gestión de roles:** identificación del Creador y de los Integrantes dentro del proyecto.

### 9.4 Generación de la planificación con IA
- **Validación de información suficiente:** el sistema verifica que existan los datos mínimos para generar la planificación.
- **Generación asistida por IA:** análisis de la información del proyecto y del equipo mediante un LLM (una sola llamada) para producir una propuesta de planificación en formato JSON.
- **Validación de la salida:** comprobación de dependencias sin ciclos, responsables válidos y duraciones positivas.
- **Cálculo de fechas:** el backend convierte duraciones y orden en fechas estimadas, hacia atrás desde la fecha de entrega.

### 9.5 Revisión, edición y aprobación de la planificación
- **Visualización de la propuesta:** etapas, tareas, responsables, prioridades, dependencias, orden y fechas.
- **Edición manual:** el Creador ajusta la propuesta sin volver a llamar a la IA.
- **Confirmación individual:** cada integrante acepta o solicita cambios sobre sus tareas.
- **Aprobación de la planificación:** cuando todos confirman, la planificación se activa.

### 9.6 Gestión de tareas
- **Administración de tareas:** crear, editar y eliminar tareas con nombre, descripción, responsable, estado, prioridad, fechas y dependencias.
- **Control de estados:** Pendiente, En progreso, Bloqueada y Completada, con transiciones permitidas.
- **Control de dependencias:** bloqueo y desbloqueo automático según el avance de las tareas previas.
- **Vista "Mis tareas":** cada integrante visualiza las tareas que le fueron asignadas.

### 9.7 Notificaciones
- **Notificaciones in-app:** avisos sobre asignaciones, cambios de estado, habilitación de tareas bloqueadas, cambios de fecha o responsable y cambios importantes en la planificación.
