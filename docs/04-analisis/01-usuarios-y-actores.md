# 01 — Usuarios y actores

## 7. Usuarios del sistema

El sistema está dirigido principalmente a **estudiantes de educación superior** (universidades e institutos) que desarrollan proyectos de software en equipo como parte de su formación académica.

**Características de los usuarios:**

- Son estudiantes de carreras relacionadas con ingeniería de software, sistemas o afines.
- Trabajan en equipos de dos o más integrantes (comúnmente entre 3 y 5).
- Poseen distintos niveles de habilidad técnica, desde principiantes hasta avanzados.
- Generalmente tienen poca o nula experiencia formal en gestión de proyectos.
- Necesitan organizar sus actividades, distribuir responsabilidades y cumplir una fecha de entrega.

**Qué debe poder hacer cada usuario en el sistema:**

- Registrarse e iniciar sesión con correo electrónico y contraseña.
- Completar su perfil con sus habilidades y conocimientos mediante un catálogo.
- Crear un proyecto (convirtiéndose en su Creador) o unirse a uno como Integrante invitado.

En esta versión no se contemplan otros tipos de usuario (por ejemplo, docentes o administradores de la plataforma).

---

## 8. Actores principales

Los actores representan los roles que interactúan con el sistema. Un mismo usuario puede desempeñar el rol de **Creador** en un proyecto y de **Integrante** en otro.

### Actor 1 — Creador

Usuario que crea el proyecto y actúa como su administrador. Sus responsabilidades:

- Crear y editar la información del proyecto.
- Invitar o eliminar integrantes.
- Generar la planificación mediante IA.
- Editar manualmente la propuesta de planificación.
- Cambiar el estado de cualquier tarea.

### Actor 2 — Integrante

Miembro del equipo invitado al proyecto. Sus responsabilidades:

- Completar su perfil de habilidades.
- Visualizar el proyecto y la planificación.
- Aceptar o solicitar cambios sobre sus tareas asignadas.
- Cambiar el estado de sus propias tareas.
- Recibir notificaciones sobre cambios y avances.

### Actor 3 — Sistema / IA (actor interno)

Actor no humano que interactúa con el backend:

- La **inteligencia artificial (LLM)** analiza la información del proyecto y las habilidades del equipo, y genera la propuesta de planificación en formato JSON.
- El **backend** valida la salida de la IA, calcula las fechas y controla las dependencias y notificaciones.

> **Nota:** "Creador" e "Integrante" son **roles dentro de un proyecto**, no tipos de cuenta distintos. Cualquier usuario registrado puede crear proyectos o ser invitado a ellos.
