# ============================================================
#  Prueba de flujo completo de Colab (backend-usuario)
#
#  Recorre el flujo real de la app:
#    registro/login -> habilidades -> proyecto -> invitacion ->
#    generar planificacion con IA (TECSUP "La BestIA")
#
#  Requisitos:
#    - Backend corriendo en http://localhost:8081
#    - Arrancado con IA_PROVIDER=real y USER_API_KEY definidos
#      para que la planificacion se genere con la IA real.
#
#  Uso:
#    cd backend-usuario
#    .\test-flujo-completo.ps1
#
#  Opciones:
#    .\test-flujo-completo.ps1 -BaseUrl http://localhost:8081
# ============================================================

param(
    [string]$BaseUrl      = "http://localhost:8081",
    [string]$EmailCreador = "creador@test.com",
    [string]$EmailIntegr  = "integ@test.com",
    [string]$Password     = "secreto123"
)

$ErrorActionPreference = "Stop"

function ToJson($obj) { return $obj | ConvertTo-Json -Depth 8 -Compress }

# Registra; si el email ya existe, hace login para recuperar el token.
function RegistrarOLoguear($email, $nombre) {
    $reg = ToJson @{ email = $email; contrasena = $Password; nombre = $nombre }
    try {
        $r = Invoke-RestMethod -Uri "$BaseUrl/api/auth/registro" -Method Post -ContentType "application/json" -Body $reg
        Write-Host "  [OK] Registro: $email" -ForegroundColor Green
        return $r.token
    }
    catch {
        $login = ToJson @{ email = $email; contrasena = $Password }
        $r = Invoke-RestMethod -Uri "$BaseUrl/api/auth/login" -Method Post -ContentType "application/json" -Body $login
        Write-Host "  [OK] Login (ya existia): $email" -ForegroundColor Yellow
        return $r.token
    }
}

function AgregarHabilidad($headers, $id, $nombre) {
    try {
        Invoke-WebRequest -Uri "$BaseUrl/api/usuario/habilidades/$id" -Method Put -Headers $headers -UseBasicParsing | Out-Null
        Write-Host "  [OK] Habilidad: $nombre (id $id)" -ForegroundColor Green
    }
    catch {
        Write-Host "  [~] Habilidad $nombre (id $id): ya agregada o no disponible" -ForegroundColor Yellow
    }
}

Write-Host ""
Write-Host "=== 1. Autenticacion ===" -ForegroundColor Cyan
$tok1 = RegistrarOLoguear $EmailCreador "Creador"
$tok2 = RegistrarOLoguear $EmailIntegr  "Integrante"
$h1 = @{ Authorization = "Bearer $tok1" }
$h2 = @{ Authorization = "Bearer $tok2" }

Write-Host ""
Write-Host "=== 2. Perfil de habilidades ===" -ForegroundColor Cyan
AgregarHabilidad $h1 1 "Java"
AgregarHabilidad $h1 9 "Analisis"
AgregarHabilidad $h2 5 "React"
AgregarHabilidad $h2 8 "PostgreSQL"

Write-Host ""
Write-Host "=== 3. Crear proyecto ===" -ForegroundColor Cyan
$proyBody = ToJson @{
    nombre                      = "Proyecto Demo Colab"
    descripcion                 = "Prueba end-to-end del flujo"
    objetivoGeneral             = "Construir una aplicacion web de gestion de proyectos"
    fechaEntrega                = "2026-12-15"
    tecnologiasIds              = @(1, 5)
    requerimientosFuncionales   = @(@{ descripcion = "El usuario puede iniciar sesion"; codigo = "RF-01" })
    requerimientosNoFuncionales = @()
    casosDeUso                  = @()
}
$proy = Invoke-RestMethod -Uri "$BaseUrl/api/proyectos" -Method Post -Headers $h1 -ContentType "application/json" -Body $proyBody
$pid = $proy.id
Write-Host "  [OK] Proyecto creado (id $pid)" -ForegroundColor Green

Write-Host ""
Write-Host "=== 4. Invitar y aceptar ===" -ForegroundColor Cyan
$invBody = ToJson @{ email = $EmailIntegr }
Invoke-WebRequest -Uri "$BaseUrl/api/proyectos/$pid/invitaciones" -Method Post -Headers $h1 -ContentType "application/json" -Body $invBody -UseBasicParsing | Out-Null
Write-Host "  [OK] Invitacion enviada a $EmailIntegr" -ForegroundColor Green

$invs = Invoke-RestMethod -Uri "$BaseUrl/api/invitaciones" -Method Get -Headers $h2
$miInv = $invs | Where-Object { $_.proyectoId -eq $pid } | Select-Object -First 1
if (-not $miInv) { throw "No se encontro la invitacion para el proyecto $pid" }
Invoke-WebRequest -Uri "$BaseUrl/api/invitaciones/$($miInv.integranteId)/aceptar" -Method Post -Headers $h2 -UseBasicParsing | Out-Null
Write-Host "  [OK] Invitacion aceptada por $EmailIntegr" -ForegroundColor Green

Write-Host ""
Write-Host "=== 5. Generar planificacion con la IA (TECSUP) ===" -ForegroundColor Cyan
Write-Host "  (puede tardar unos segundos...)" -ForegroundColor DarkGray
$plan = Invoke-RestMethod -Uri "$BaseUrl/api/proyectos/$pid/planificacion" -Method Post -Headers $h1
Write-Host "  [OK] Planificacion generada. Estado: $($plan.estado)" -ForegroundColor Green

Write-Host ""
Write-Host "--- Etapas y tareas generadas por la IA ---" -ForegroundColor White
$plan | ConvertTo-Json -Depth 10
Write-Host ""

Write-Host "=== 6. Confirmacion (opcional) ===" -ForegroundColor Cyan
try {
    $conf = Invoke-RestMethod -Uri "$BaseUrl/api/proyectos/$pid/planificacion/confirmar" -Method Post -Headers $h1
    Write-Host "  [OK] Confirmacion del creador: $($conf.estado)" -ForegroundColor Green
}
catch {
    Write-Host "  [~] Creador sin tareas asignadas (no se pudo confirmar)." -ForegroundColor Yellow
}
try {
    $conf2 = Invoke-RestMethod -Uri "$BaseUrl/api/proyectos/$pid/planificacion/confirmar" -Method Post -Headers $h2
    Write-Host "  [OK] Confirmacion del integrante: $($conf2.estado)" -ForegroundColor Green
}
catch {
    Write-Host "  [~] Integrante sin tareas asignadas (no se pudo confirmar)." -ForegroundColor Yellow
}

Write-Host ""
Write-Host "=== Resumen ===" -ForegroundColor Cyan
Write-Host "  Proyecto id      : $pid"
Write-Host "  Creador          : $EmailCreador"
Write-Host "  Integrante       : $EmailIntegr"
Write-Host "  Etapas generadas : $($plan.etapas.Count)"
Write-Host ""
Write-Host "FLUJO COMPLETO OK." -ForegroundColor Green
