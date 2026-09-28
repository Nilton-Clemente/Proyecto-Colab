# Comandos principales — Backend Colab (backend-usuario)

Guía rápida de los comandos para ejecutar y probar el backend de usuario.
Ruta base del backend: `C:\Users\Alumno\Desktop\XD\Proyecto-Colab\backend-usuario`

---

## 1. Requisitos del entorno

| Herramienta | Ubicación / valor |
|---|---|
| **JDK 21** | `C:\Users\Alumno\.jdks\jdk-21.0.12.1+1` |
| **PostgreSQL 18** | BD `colab`, esquema `usuario`, usuario `postgres` (contraseña en `application.yml`) |
| **Red** | Red del instituto (solo para la IA real de TECSUP) |

---

## 2. Arrancar el backend

### Con IA real (TECSUP "La BestIA")

```powershell
cd C:\Users\Alumno\Desktop\XD\Proyecto-Colab\backend-usuario

$env:JAVA_HOME="C:\Users\Alumno\.jdks\jdk-21.0.12.1+1"
$env:PATH="$env:JAVA_HOME\bin;$env:PATH"
$env:IA_PROVIDER="real"
$env:USER_API_KEY="TU_CLAVE_AQUI"

.\mvnw.cmd spring-boot:run
```

### Sin IA (modo mock, para pruebas sin red)

```powershell
cd C:\Users\Alumno\Desktop\XD\Proyecto-Colab\backend-usuario
$env:JAVA_HOME="C:\Users\Alumno\.jdks\jdk-21.0.12.1+1"
$env:PATH="$env:JAVA_HOME\bin;$env:PATH"
.\mvnw.cmd spring-boot:run
```

El backend arranca en el puerto **8081**. Señal de éxito:

```
Tomcat started on port 8081
Started BackendUsuarioApplication in X.X seconds
```

---

## 3. Verificar el entorno

```powershell
java -version              # debe decir 21.0.12.1 (si no, fijar JAVA_HOME)
Get-ExecutionPolicy -List  # ver política de ejecución de scripts
```

---

## 4. Compilar y ejecutar tests

```powershell
.\mvnw.cmd compile      # solo compilar
.\mvnw.cmd test         # tests unitarios (6 tests)
```

---

## 5. Scripts de prueba (en backend-usuario)

```powershell
.\consola-colab.ps1          # CLI interactiva (menú)
.\test-flujo-completo.ps1    # flujo end-to-end automático (registro -> planificación IA)
.\test-ia-conexion.ps1       # probar conexión directa a TECSUP (sin backend)
```

Para cambiar la URL base (opcional):

```powershell
.\consola-colab.ps1 -BaseUrl http://localhost:8081
```

---

## 6. Consola interactiva — opciones del menú

```
 1. Registrarme
 2. Iniciar sesion
 3. Usar token manual
 4. Ver proveedor de IA
 5. Probar IA (demo)
 6. Listar mis proyectos
 7. Crear proyecto
 8. Ver proyecto (por id)
 9. Generar planificacion (IA)
10. Ver planificacion
11. Confirmar planificacion
12. Agregar habilidad a mi perfil
13. Listar notificaciones
14. Listar tareas de un proyecto
15. Invitar integrante a un proyecto
16. Aceptar invitacion
 0. Salir
```

---

## 7. Configuración permanente (una sola vez)

Con `setx` las variables quedan guardadas para ventanas **nuevas**:

```powershell
setx JAVA_HOME "C:\Users\Alumno\.jdks\jdk-21.0.12.1+1"
setx IA_PROVIDER "real"
setx USER_API_KEY "TU_CLAVE_AQUI"
```

---

## 8. Habilitar ejecución de scripts (una sola vez)

Si PowerShell bloquea los `.ps1`:

```powershell
Set-ExecutionPolicy -Scope CurrentUser -ExecutionPolicy RemoteSigned -Force
```

---

## 9. Probar la IA sin abrir la consola

```powershell
# Ver proveedor activo (debe decir "real")
Invoke-RestMethod -Uri http://localhost:8081/api/ia/proveedor -Method Get

# Generar texto con la IA
$body = @{ mensaje = "Responde con una sola frase: funciona?" } | ConvertTo-Json
Invoke-RestMethod -Uri http://localhost:8081/api/ia/demo -Method Post -ContentType "application/json" -Body $body
```

---

## 10. Endpoints y documentación

| Recurso | URL |
|---|---|
| **Swagger UI** (documentación interactiva) | `http://localhost:8081/swagger-ui.html` |
| Contrato OpenAPI (JSON) | `http://localhost:8081/v3/api-docs` |
| Demo IA | `POST http://localhost:8081/api/ia/demo` |
| Registro / Login | `POST /api/auth/registro` · `POST /api/auth/login` |

---

## 11. Variables de entorno del backend (application.yml)

| Variable | Descripción | Valor por defecto |
|---|---|---|
| `IA_PROVIDER` | `mock` o `real` | `mock` |
| `USER_API_KEY` | Token de "La BestIA" (TECSUP) | *(vacío)* |
| `OPENWEBUI_URL` | URL base de OpenWebUI | `http://192.168.17.11:3000` |
| `MODEL` | Modelo de IA | `Qwen/Qwen3.6-35B-A3B-FP8` |
| `DB_USERNAME` | Usuario de PostgreSQL | `postgres` |
| `JWT_SECRET` | Clave para firmar JWT | clave de desarrollo |
| `JWT_EXPIRACION_MS` | Expiración del token | `86400000` (24 h) |

---

## 12. Notas importantes

- La **API key** va solo en la variable de entorno `USER_API_KEY`; nunca en código ni en el repositorio.
- Las **migraciones Flyway** se aplican automáticamente al arrancar (esquema `usuario`).
- Para **generar planificación** (regla RN-18) el proyecto necesita: `objetivoGeneral`, `fechaEntrega`, ≥1 requerimiento funcional, ≥1 tecnología y **≥2 integrantes con habilidades**.
- Si el puerto `8081` está ocupado, detener el proceso anterior (`Ctrl+C`) o matarlo antes de re-arrancar.
