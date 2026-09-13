# 03 — Objetivos y alcance

## 4. Objetivo general

Desarrollar una aplicación **móvil y web** que, mediante el uso de inteligencia artificial, permita a los equipos de estudiantes generar propuestas de planificación personalizadas para sus proyectos de software, y que facilite el seguimiento del avance mediante la gestión de tareas, dependencias y responsabilidades.

---

## 5. Objetivos específicos

1. Implementar el registro e inicio de sesión de usuarios mediante correo electrónico y contraseña.
2. Permitir la creación y administración de proyectos de software con su información estructurada (objetivos, requerimientos, tecnologías, restricciones y fecha de entrega).
3. Permitir la gestión de los integrantes del equipo y el registro de sus habilidades mediante un catálogo.
4. Integrar el modelo de lenguaje (LLM) institucional de TECSUP — "La BestIA" (OpenWebUI) — para que genere una propuesta de planificación en formato JSON estructurado, a partir de la información del proyecto y de las habilidades del equipo.
5. Validar la salida de la IA (dependencias sin ciclos, responsables existentes y duraciones positivas) antes de presentarla al equipo.
6. Calcular en el backend las fechas estimadas de las tareas, a partir de las duraciones y el orden propuestos por la IA, en función de la fecha de entrega.
7. Permitir la revisión, edición manual y confirmación individual de la planificación por parte de cada integrante.
8. Implementar la gestión de tareas con estados controlados (Pendiente, En progreso, Bloqueada, Completada) y el control de dependencias con bloqueo y desbloqueo automático.
9. Implementar notificaciones internas (in-app) sobre eventos relevantes (asignación, cambio de estado, habilitación de tareas, cambio de fecha o responsable).
10. Definir e implementar los roles y permisos del sistema (Creador e Integrante).
11. Implementar la plataforma de administración (frontend React + backend Django) para las operaciones administrativas del sistema.

---

## 6. Alcance y limitaciones

**Alcance (incluido en esta versión)**

- Aplicación móvil Android (Kotlin + Jetpack Compose) para los usuarios.
- Aplicación web (React) para los usuarios, compartiendo el mismo backend (Spring Boot).
- Backend Java + Spring Boot con API REST (área de Usuario).
- Plataforma de administración: backend Django + frontend React (área de Administración).
- Base de datos PostgreSQL compartida, con esquemas separados: `usuario` (Spring Boot + JPA/Hibernate + Flyway) y `admin` (Django + migraciones de Django).
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

- El servicio de IA de TECSUP ("La BestIA") solo es accesible desde la red del instituto (laboratorios de TD o red WiFi "Comunidad Innovadores").
- No incluye otros tipos de proyecto (IoT, investigación, empresarial), aunque el diseño se prepara para incorporarlos en el futuro.
- No incluye notificaciones push (solo in-app).
- No incluye autenticación con Google.
- No incluye re-planificación automática con IA (la generación se realiza una sola vez).
- No incluye colaboración en tiempo real.
- No incluye chat o mensajería interna.
- No incluye adjuntos ni subtareas en las tareas.
- No incluye cronograma (Gantt) avanzado ni exportación de reportes.
- Estados de tarea limitados a cuatro; la tarea "Completada" no es reversible.

> **Plataforma de administración (parte del MVP):** la arquitectura incluye una **plataforma de
> administración** (frontend React + backend Django) que forma parte del entregable. Su alcance
> funcional detallado se define en el [05 — Diseño](05-diseno/README.md) y [06 — Gestión](06-gestion.md) (ver
> [ARQUITECTURA](ARQUITECTURA.md)).
