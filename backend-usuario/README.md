# Backend de Usuario (Spring Boot)

Backend del área de **Usuario**. Expone la API REST que consumen la app móvil y la web de usuario.

- **Responsable:** Persona 1
- **Tecnología:** Java + Spring Boot, JPA/Hibernate, Flyway, PostgreSQL (esquema `usuario`).

## Puesta en marcha

1. Generar el proyecto con [Spring Initializr](https://start.spring.io/) dentro de esta carpeta.
   - Build: Maven o Gradle (a elección del equipo).
   - Dependencias: Web, Data JPA, PostgreSQL, Flyway, Security, Validation, Lombok (opcional).
2. Configurar la conexión a PostgreSQL (esquema `usuario`).

## Responsabilidades principales

Autenticación JWT, roles, integración IA ("La BestIA" + mock), cálculo de fechas, planificación, tareas/dependencias y notificaciones. Publica el contrato OpenAPI/Swagger.

Ver acuerdos en el [README raíz](../README.md), el [modelo de entidades](../docs/05-diseno/01-modelo-de-entidades.md) y la [arquitectura](../docs/ARQUITECTURA.md).
