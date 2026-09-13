# 06 — Gestión

## 18. Priorización de funcionalidades para el MVP

El alcance completo está definido en [03 — Objetivos y alcance](../03-objetivos-y-alcance.md) (secciones 5 y 6). Aquí se prioriza **cómo atacar el desarrollo** dentro de ese alcance.

### 18.1 Dentro del MVP (imprescindible)

| # | Funcionalidad | Módulo (análisis) |
|---|---|---|
| 1 | Registro e inicio de sesión (correo + contraseña) | 9.1 |
| 2 | Perfil de habilidades mediante catálogo | 9.1 |
| 3 | Crear / editar / visualizar proyecto y su información | 9.2 |
| 4 | Invitar / aceptar integrantes (roles Creador e Integrante) | 9.3 |
| 5 | Generar planificación con IA (una sola vez), con implementación mock | 9.4 |
| 6 | Validar salida de la IA y calcular fechas (días naturales) | 9.4 |
| 7 | Revisión / edición manual + confirmación individual | 9.5 |
| 8 | Gestión de tareas (estados, dependencias, bloqueo) | 9.6 |
| 9 | Notificaciones in-app | 9.7 |
| 10 | Plataforma de administración (React + Django) — funciones por definir | — |

### 18.2 Diferido (fuera del MVP)

Ya listado en [03 — Objetivos y alcance](../03-objetivos-y-alcance.md): autenticación con Google, notificaciones push, re-planificación con IA, colaboración en tiempo real, chat, adjuntos/subtareas, Gantt avanzado y exportación de reportes.

### 18.3 Orden sugerido de implementación (incrementos)

1. **Fase 0 — Base:** repos, CI, base de datos (Flyway, esquema `usuario`), autenticación JWT y contrato OpenAPI inicial.
2. **Fase 1 — Núcleo de dominio:** proyectos, integrantes y perfil de habilidades.
3. **Fase 2 — IA y planificación:** generación (mock → real), validación, cálculo de fechas y confirmación.
4. **Fase 3 — Ejecución:** gestión de tareas, dependencias y notificaciones.
5. **Fase 4 — Administración:** funcionalidad del área admin (a definir en el diseño).

## 19. División inicial del trabajo (3 integrantes)

División por componente, siguiendo la arquitectura ([ARQUITECTURA](../ARQUITECTURA.md)).

| Persona | Componente(s) | Responsabilidades principales |
|---|---|---|
| **Persona 1 (tú)** | Backend de Usuario | Spring Boot + API REST; PostgreSQL esquema `usuario` (JPA/Hibernate + Flyway); autenticación JWT y roles; integración IA "La BestIA" (+ mock); validación de salida y cálculo de fechas; tareas/dependencias/notificaciones; contrato OpenAPI/Swagger. |
| **Persona 2** | App móvil (Kotlin + Jetpack Compose) | Todas las pantallas de usuario en móvil (auth, proyectos, equipo, generación, "Mis tareas", notificaciones); consumo de la API de Spring Boot. |
| **Persona 3** | Web de usuario (React) + Administración (React + Django) | Web de usuario (React) contra Spring Boot; plataforma de administración completa: frontend React + backend Django + esquema `admin`. |

### 19.1 Acuerdos transversales

- **Contrato de API primero:** la Persona 1 define y publica el OpenAPI; las Personas 2 y 3 consumen únicamente ese contrato (no modifican el backend).
- **IA con mock por defecto:** hasta estar en la red de TECSUP se trabaja con `MockPlanIAClient`; la comunicación real se valida por niveles (ver [08 — Integración de la IA](../04-analisis/08-integracion-ia.md)).
- **Base de datos compartida con esquemas separados:** Spring Boot (Flyway) gestiona `usuario`; Django (migraciones) gestiona `admin` (ver [ARQUITECTURA](../ARQUITECTURA.md#4-base-de-datos-compartida)).
- **Convenciones de código y ramas Git** por componente.

