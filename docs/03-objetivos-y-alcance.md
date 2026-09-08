# 03 — Objetivos y alcance

## 4. Objetivo general

Desarrollar una aplicación móvil que, mediante el uso de inteligencia artificial, permita a los equipos de estudiantes generar propuestas de planificación personalizadas para sus proyectos de software, y que facilite el seguimiento del avance mediante la gestión de tareas, dependencias y responsabilidades.

---

## 5. Objetivos específicos

1. Implementar el registro e inicio de sesión de usuarios mediante correo electrónico y contraseña.
2. Permitir la creación y administración de proyectos de software con su información estructurada (objetivos, requerimientos, tecnologías, restricciones y fecha de entrega).
3. Permitir la gestión de los integrantes del equipo y el registro de sus habilidades mediante un catálogo.
4. Integrar un modelo de lenguaje (LLM) que genere una propuesta de planificación en formato JSON estructurado, a partir de la información del proyecto y de las habilidades del equipo.
5. Validar la salida de la IA (dependencias sin ciclos, responsables existentes y duraciones positivas) antes de presentarla al equipo.
6. Calcular en el backend las fechas estimadas de las tareas, a partir de las duraciones y el orden propuestos por la IA, en función de la fecha de entrega.
7. Permitir la revisión, edición manual y confirmación individual de la planificación por parte de cada integrante.
8. Implementar la gestión de tareas con estados controlados (Pendiente, En progreso, Bloqueada, Completada) y el control de dependencias con bloqueo y desbloqueo automático.
9. Implementar notificaciones internas (in-app) sobre eventos relevantes (asignación, cambio de estado, habilitación de tareas, cambio de fecha o responsable).
10. Definir e implementar los roles y permisos del sistema (Creador e Integrante).

---

## 6. Alcance y limitaciones

**Alcance (incluido en esta versión)**

- Aplicación móvil Android (Kotlin + Jetpack Compose) como única interfaz del sistema.
- Backend Java + Spring Boot con API REST.
- Base de datos PostgreSQL (JPA/Hibernate + Flyway).
- Autenticación por correo electrónico y contraseña.
- Proyectos exclusivamente de software académico.
- Registro de la información del proyecto: problemática, descripción, objetivo general, objetivos específicos, alcance, requerimientos funcionales, requerimientos no funcionales, casos de uso, tecnologías, restricciones y fecha de entrega.
- Gestión de integrantes y perfiles de habilidades mediante un catálogo.
- Generación de la planificación con IA (una sola llamada; salida JSON validada).
- Revisión, edición manual y confirmación individual de la planificación.
- Gestión de tareas con cuatro estados y dependencias (finish-to-start, sin ciclos).
- Notificaciones internas (in-app).
- Roles: Creador e Integrante.

**Limitaciones (fuera de esta versión)**

- No incluye aplicación web.
- No incluye otros tipos de proyecto (IoT, investigación, empresarial), aunque el diseño se prepara para incorporarlos en el futuro.
- No incluye notificaciones push (solo in-app).
- No incluye autenticación con Google.
- No incluye re-planificación automática con IA (la generación se realiza una sola vez).
- No incluye colaboración en tiempo real.
- No incluye chat o mensajería interna.
- No incluye adjuntos ni subtareas en las tareas.
- No incluye cronograma (Gantt) avanzado ni exportación de reportes.
- Estados de tarea limitados a cuatro; la tarea "Completada" no es reversible.
