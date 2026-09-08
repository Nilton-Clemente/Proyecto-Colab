# 04 — Requerimientos no funcionales

## 11. Requerimientos no funcionales

Los requerimientos no funcionales describen atributos de calidad del sistema. Se identifican con el formato `RNF-XX` y se agrupan por categoría.

### 11.1 Rendimiento

- **RNF-01 — Tiempo de respuesta de la API.** Las operaciones CRUD del backend deben responder en un tiempo razonable (por ejemplo, menos de 2 segundos en condiciones normales).
- **RNF-02 — Generación asíncrona con IA.** La generación de la planificación con IA puede tardar más tiempo; debe ejecutarse de forma asíncrona y mostrar un estado de progreso, sin bloquear la interfaz.
- **RNF-03 — Fluidez de la aplicación.** La aplicación móvil debe responder de forma fluida a las interacciones del usuario (navegación y carga de listas) sin bloqueos perceptibles.

### 11.2 Seguridad

- **RNF-04 — Protección de contraseñas.** Las contraseñas deben almacenarse cifradas (hash) en la base de datos.
- **RNF-05 — Autenticación por tokens.** El acceso a la API debe protegerse mediante tokens (JWT) y las sesiones deben poder expirar.
- **RNF-06 — Control de acceso.** Un usuario solo debe poder acceder a los proyectos a los que pertenece, respetando su rol.
- **RNF-07 — Protección de credenciales de la IA.** La clave de la API del LLM debe residir únicamente en el backend y no exponerse en la aplicación móvil.

### 11.3 Usabilidad

- **RNF-08 — Interfaz intuitiva.** La aplicación debe ser sencilla de usar para usuarios sin experiencia en gestión de proyectos.
- **RNF-09 — Idioma.** La interfaz y el contenido generado deben estar en español.
- **RNF-10 — Retroalimentación.** El sistema debe mostrar mensajes claros ante errores, validaciones y estados de carga.

### 11.4 Disponibilidad y confiabilidad

- **RNF-11 — Persistencia de datos.** Los datos del proyecto deben persistir de forma confiable en la base de datos.
- **RNF-12 — Manejo de fallos de la IA.** Si la IA falla o devuelve una salida inválida, el sistema debe notificar el error y permitir reintentar, sin perder los datos del proyecto.
- **RNF-13 — Consistencia.** Las operaciones sobre tareas y dependencias deben mantener la integridad de los datos (sin ciclos ni estados inválidos).

### 11.5 Mantenibilidad

- **RNF-14 — Código modular.** El backend y la aplicación deben estructurarse en capas o módulos para facilitar su mantenimiento.
- **RNF-15 — Migraciones versionadas.** Los cambios en el esquema de la base de datos deben gestionarse con Flyway.
- **RNF-16 — Documentación de la API.** El sistema debe contar con documentación de la API (por ejemplo, OpenAPI/Swagger).

### 11.6 Portabilidad y compatibilidad

- **RNF-17 — Compatibilidad Android.** La aplicación debe funcionar en un rango definido de versiones de Android (por ejemplo, Android 8.0 / API 26 o superior).
- **RNF-18 — Arquitectura cliente-servidor.** La comunicación entre la aplicación y el backend debe realizarse mediante una API REST con formato JSON.
