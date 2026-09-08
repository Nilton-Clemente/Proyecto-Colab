# 05 — Casos de uso

## 12. Casos de uso

Los casos de uso describen las interacciones entre los actores y el sistema. Se identifican con el formato `CU-XX`.

### Resumen de casos de uso

| ID | Caso de uso | Actor principal |
|---|---|---|
| CU-01 | Registrarse | Visitante |
| CU-02 | Iniciar sesión | Usuario |
| CU-03 | Gestionar perfil de habilidades | Usuario |
| CU-04 | Crear proyecto | Usuario |
| CU-05 | Gestionar información del proyecto | Creador |
| CU-06 | Invitar integrantes | Creador |
| CU-07 | Aceptar invitación | Usuario invitado |
| CU-08 | Generar planificación con IA | Creador |
| CU-09 | Editar la planificación propuesta | Creador |
| CU-10 | Confirmar tareas asignadas | Integrante |
| CU-11 | Gestionar tareas | Creador |
| CU-12 | Cambiar estado de una tarea | Integrante, Creador |
| CU-13 | Consultar mis tareas | Integrante |
| CU-14 | Consultar notificaciones | Integrante, Creador |

---

### CU-01 — Registrarse

- **Actores:** Visitante.
- **Descripción:** Permite a una persona crear una cuenta en el sistema.
- **Precondiciones:** Ninguna.
- **Flujo principal:**
  1. El visitante accede a la pantalla de registro.
  2. Ingresa correo electrónico y contraseña.
  3. El sistema valida los datos y crea la cuenta.
- **Flujos alternativos:**
  - Si el correo ya está registrado, el sistema muestra un mensaje de error.
  - Si los datos no son válidos, el sistema solicita corregirlos.
- **Postcondiciones:** El usuario queda registrado y puede iniciar sesión.

### CU-02 — Iniciar sesión

- **Actores:** Usuario.
- **Descripción:** Permite a un usuario autenticarse en el sistema.
- **Precondiciones:** El usuario debe estar registrado.
- **Flujo principal:**
  1. El usuario ingresa correo y contraseña.
  2. El sistema valida las credenciales.
  3. El sistema genera un token y muestra la pantalla principal.
- **Flujos alternativos:**
  - Si las credenciales son incorrectas, se muestra un mensaje de error.
- **Postcondiciones:** El usuario queda autenticado.

### CU-03 — Gestionar perfil de habilidades

- **Actores:** Usuario.
- **Descripción:** Permite registrar y editar las habilidades del usuario mediante un catálogo.
- **Precondiciones:** El usuario debe estar autenticado.
- **Flujo principal:**
  1. El usuario accede a su perfil.
  2. Selecciona habilidades del catálogo (tecnologías y áreas).
  3. Guarda los cambios.
- **Flujos alternativos:**
  - El usuario puede eliminar habilidades ya registradas.
- **Postcondiciones:** El perfil queda actualizado y disponible para la generación de planificaciones.

### CU-04 — Crear proyecto

- **Actores:** Usuario.
- **Descripción:** Permite crear un proyecto de software.
- **Precondiciones:** El usuario debe estar autenticado.
- **Flujo principal:**
  1. El usuario selecciona "Crear proyecto".
  2. Ingresa el nombre del proyecto.
  3. El sistema crea el proyecto y le asigna el rol de Creador.
- **Flujos alternativos:**
  - Si el nombre está vacío, se solicita completarlo.
- **Postcondiciones:** El proyecto queda creado en estado BORRADOR.

### CU-05 — Gestionar información del proyecto

- **Actores:** Creador.
- **Descripción:** Permite registrar y editar la información estructurada del proyecto.
- **Precondiciones:** El proyecto debe existir y el usuario ser su Creador.
- **Flujo principal:**
  1. El Creador abre el proyecto.
  2. Registra o edita la información (objetivos, alcance, requerimientos, tecnologías, restricciones, fecha de entrega).
  3. Guarda los cambios.
- **Flujos alternativos:**
  - El sistema valida los campos obligatorios.
- **Postcondiciones:** La información del proyecto queda actualizada.

### CU-06 — Invitar integrantes

- **Actores:** Creador.
- **Descripción:** Permite agregar integrantes al equipo del proyecto.
- **Precondiciones:** El proyecto debe existir y el usuario ser su Creador.
- **Flujo principal:**
  1. El Creador selecciona "Invitar integrantes".
  2. Ingresa el correo del usuario a invitar.
  3. El sistema envía la invitación.
- **Flujos alternativos:**
  - Si el correo no corresponde a un usuario registrado, se notifica.
- **Postcondiciones:** El usuario queda invitado, pendiente de aceptación.

### CU-07 — Aceptar invitación

- **Actores:** Usuario invitado.
- **Descripción:** Permite a un usuario aceptar la invitación a un proyecto.
- **Precondiciones:** El usuario debe haber recibido una invitación.
- **Flujo principal:**
  1. El usuario visualiza la invitación.
  2. Acepta la invitación.
  3. El sistema lo agrega como Integrante del proyecto.
- **Flujos alternativos:**
  - El usuario puede rechazar la invitación.
- **Postcondiciones:** El usuario pasa a ser Integrante del proyecto.

### CU-08 — Generar planificación con IA

- **Actores:** Creador (Sistema/IA).
- **Descripción:** Permite generar una propuesta de planificación mediante IA.
- **Precondiciones:** El proyecto debe cumplir la información mínima (objetivo general, al menos un requerimiento funcional, una tecnología, fecha de entrega y al menos dos integrantes con perfil).
- **Flujo principal:**
  1. El Creador solicita "Generar planificación".
  2. El sistema valida la información mínima.
  3. El sistema llama a la IA, que genera la propuesta en JSON.
  4. El sistema valida la salida y calcula las fechas.
  5. El sistema guarda la planificación en estado PROPUESTA.
- **Flujos alternativos:**
  - Si falta información mínima, se indica qué falta.
  - Si la IA falla o devuelve una salida inválida, se muestra un error y se permite reintentar.
- **Postcondiciones:** Existe una planificación en estado PROPUESTA.

### CU-09 — Editar la planificación propuesta

- **Actores:** Creador.
- **Descripción:** Permite ajustar manualmente la planificación antes de su aprobación.
- **Precondiciones:** Debe existir una planificación en estado PROPUESTA.
- **Flujo principal:**
  1. El Creador visualiza la propuesta.
  2. Edita tareas, responsables, prioridades o dependencias.
  3. Guarda los cambios.
- **Flujos alternativos:**
  - El sistema valida que no se creen ciclos en las dependencias.
- **Postcondiciones:** La propuesta queda ajustada y lista para confirmación.

### CU-10 — Confirmar tareas asignadas

- **Actores:** Integrante.
- **Descripción:** Permite a cada integrante aceptar o solicitar cambios sobre sus tareas.
- **Precondiciones:** Debe existir una planificación en estado PROPUESTA y el integrante debe tener tareas asignadas.
- **Flujo principal:**
  1. El integrante visualiza sus tareas propuestas.
  2. Acepta sus tareas ("Acepto mis tareas").
  3. El sistema registra la confirmación.
- **Flujos alternativos:**
  - El integrante solicita cambios con un comentario; el plan vuelve a edición.
- **Postcondiciones:** El integrante queda confirmado. Cuando todos confirman, la planificación se activa.

### CU-11 — Gestionar tareas

- **Actores:** Creador.
- **Descripción:** Permite crear, editar y eliminar tareas, y definir responsables y dependencias.
- **Precondiciones:** El proyecto debe existir y el usuario ser su Creador.
- **Flujo principal:**
  1. El Creador accede a las tareas del proyecto.
  2. Crea, edita o elimina una tarea con sus datos.
  3. Asigna responsable y dependencias.
  4. Guarda los cambios.
- **Flujos alternativos:**
  - El sistema valida dependencias sin ciclos y responsables válidos.
- **Postcondiciones:** Las tareas quedan actualizadas.

### CU-12 — Cambiar estado de una tarea

- **Actores:** Integrante, Creador.
- **Descripción:** Permite actualizar el estado de una tarea según las transiciones permitidas.
- **Precondiciones:** La planificación debe estar activa y el usuario ser el responsable (o el Creador).
- **Flujo principal:**
  1. El usuario selecciona una tarea.
  2. Cambia su estado (Pendiente → En progreso → Completada, entre otras).
  3. El sistema valida la transición y guarda.
- **Flujos alternativos:**
  - Si la tarea está bloqueada por dependencias, no puede pasar a En progreso.
  - Al completar una tarea, se desbloquean automáticamente las dependientes.
- **Postcondiciones:** El estado queda actualizado y se generan notificaciones.

### CU-13 — Consultar mis tareas

- **Actores:** Integrante.
- **Descripción:** Permite ver las tareas asignadas al integrante.
- **Precondiciones:** El usuario debe ser Integrante de un proyecto.
- **Flujo principal:**
  1. El integrante accede a "Mis tareas".
  2. Visualiza sus tareas con estado y fechas.
- **Flujos alternativos:** Ninguno relevante.
- **Postcondiciones:** El integrante conoce su carga de trabajo.

### CU-14 — Consultar notificaciones

- **Actores:** Integrante, Creador.
- **Descripción:** Permite ver las notificaciones del usuario.
- **Precondiciones:** El usuario debe estar autenticado.
- **Flujo principal:**
  1. El usuario abre el panel de notificaciones.
  2. Visualiza las notificaciones (leídas y no leídas).
  3. Marca las notificaciones como leídas.
- **Flujos alternativos:** Ninguno relevante.
- **Postcondiciones:** Las notificaciones quedan marcadas como leídas.
