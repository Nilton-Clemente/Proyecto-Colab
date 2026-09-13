# 05 — Diseño

Este módulo contiene el diseño del sistema: el **modelo de datos** (entidades y sus relaciones) derivado del análisis. La **arquitectura general** ya está documentada por separado en [ARQUITECTURA](../ARQUITECTURA.md).

## Contenido

| Documento | Elementos | Estado |
|---|---|---|
| [01 — Modelo de entidades](01-modelo-de-entidades.md) | 15. Modelo inicial de entidades | ✅ Completado |
| [02 — Relaciones](02-relaciones.md) | 16. Relaciones entre entidades | ✅ Completado |
| [ARQUITECTURA](../ARQUITECTURA.md) | 17. Arquitectura general del sistema | ✅ Documentada |

## Nota sobre la base de datos

El modelo de datos se organiza en **dos esquemas** sobre una misma instancia de PostgreSQL (ver [ARQUITECTURA](../ARQUITECTURA.md#4-base-de-datos-compartida)):

- **Esquema `usuario`** (Spring Boot + JPA/Hibernate + Flyway): entidades del área de Usuario (usuarios, proyectos, planificaciones, tareas, notificaciones).
- **Esquema `admin`** (Django + migraciones de Django): entidades del área de Administración — **alcance funcional pendiente de definición**.
