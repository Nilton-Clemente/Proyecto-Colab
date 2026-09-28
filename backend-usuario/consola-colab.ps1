# ============================================================
#  Colab - Consola de pruebas (CLI)
#
#  Interfaz por consola para probar el backend de usuario
#  mientras se termina el frontend.
#
#  Uso:
#    cd backend-usuario
#    .\consola-colab.ps1
#
#  Requisitos: backend corriendo en http://localhost:8081
# ============================================================

param(
    [string]$BaseUrl = "http://localhost:8081"
)

$ErrorActionPreference = "Stop"
$script:Token = $null
$script:Email = $null

# ---------- utilidades ----------

function LeerClave {
    $s = Read-Host "Contrasena" -AsSecureString
    $b = [System.Runtime.InteropServices.Marshal]::SecureStringToBSTR($s)
    $t = [System.Runtime.InteropServices.Marshal]::PtrToStringAuto($b)
    [System.Runtime.InteropServices.Marshal]::ZeroFreeBSTR($b)
    return $t
}

function Api($metodo, $ruta, $body = $null) {
    $headers = @{}
    if ($script:Token) { $headers.Authorization = "Bearer $script:Token" }
    $p = @{ Uri = "$BaseUrl$ruta"; Method = $metodo; Headers = $headers }
    if ($null -ne $body) {
        $json = $body | ConvertTo-Json -Depth 8 -Compress
        $p.ContentType = "application/json; charset=utf-8"
        $p.Body = [System.Text.Encoding]::UTF8.GetBytes($json)
    }
    try {
        return Invoke-RestMethod @p
    }
    catch {
        $status = $null
        if ($_.Exception.Response) { $status = $_.Exception.Response.StatusCode.value__ }
        Write-Host "  [ERROR $status] $($_.Exception.Message)" -ForegroundColor Red
        return $null
    }
}

function ApiSinCuerpo($metodo, $ruta, $body = $null) {
    $headers = @{}
    if ($script:Token) { $headers.Authorization = "Bearer $script:Token" }
    $p = @{ Uri = "$BaseUrl$ruta"; Method = $metodo; Headers = $headers; UseBasicParsing = $true }
    if ($null -ne $body) {
        $json = $body | ConvertTo-Json -Depth 8 -Compress
        $p.ContentType = "application/json; charset=utf-8"
        $p.Body = [System.Text.Encoding]::UTF8.GetBytes($json)
    }
    try {
        Invoke-WebRequest @p | Out-Null
        return $true
    }
    catch {
        $status = $null
        if ($_.Exception.Response) { $status = $_.Exception.Response.StatusCode.value__ }
        Write-Host "  [ERROR $status] $($_.Exception.Message)" -ForegroundColor Red
        return $false
    }
}

function Mostrar($obj) {
    if ($null -eq $obj) { return }
    return ($obj | ConvertTo-Json -Depth 8)
}

# ---------- acciones ----------

function Registrarme {
    $nombre = Read-Host "Nombre"
    $email  = Read-Host "Email"
    $pass   = LeerClave
    $r = Api "Post" "/api/auth/registro" @{ email = $email; contrasena = $pass; nombre = $nombre }
    if ($r) { $script:Token = $r.token; $script:Email = $email; Write-Host "  [OK] Registrado y autenticado como $email" -ForegroundColor Green }
}

function Login {
    $email = Read-Host "Email"
    $pass  = LeerClave
    $r = Api "Post" "/api/auth/login" @{ email = $email; contrasena = $pass }
    if ($r) { $script:Token = $r.token; $script:Email = $email; Write-Host "  [OK] Autenticado como $email" -ForegroundColor Green }
}

function UsarTokenManual {
    $t = Read-Host "Token JWT"
    if ($t) { $script:Token = $t; Write-Host "  [OK] Token cargado" -ForegroundColor Green }
}

function VerProveedorIA {
    $r = Api "Get" "/api/ia/proveedor"
    if ($r) { Write-Host "  Proveedor: $($r.proveedor)" -ForegroundColor Cyan }
}

function DemoIA {
    $m = Read-Host "Mensaje para la IA"
    $r = Api "Post" "/api/ia/demo" @{ mensaje = $m }
    if ($r) { Write-Host ""; Write-Host "  Respuesta: $($r.respuesta)" -ForegroundColor White; Write-Host "" }
}

function ListarProyectos {
    $r = Api "Get" "/api/proyectos"
    if (-not $r) { Write-Host "  No hay proyectos (o error)." -ForegroundColor Yellow; return }
    foreach ($p in @($r)) { Write-Host ("  [{0}] {1}" -f $p.id, $p.nombre) -ForegroundColor Green }
}

function CrearProyecto {
    $nombre   = Read-Host "Nombre"
    $objetivo = Read-Host "Objetivo general"
    $fecha    = Read-Host "Fecha de entrega (YYYY-MM-DD)"
    $tec      = Read-Host "Tecnologias (IDs separados por coma, ej: 1,5)"
    if (-not $tec) { Write-Host "  Tecnologias requeridas." -ForegroundColor Yellow; return }
    $rfsTxt = Read-Host "Requerimientos funcionales (separados por ';')"

    $tecIds = @($tec -split "," | ForEach-Object { [int]$_.Trim() })
    $rfs = @()
    foreach ($rf in ($rfsTxt -split ";")) {
        if ($rf.Trim()) { $rfs += @{ descripcion = $rf.Trim() } }
    }
    $body = @{
        nombre                      = $nombre
        objetivoGeneral             = $objetivo
        fechaEntrega                = $fecha
        tecnologiasIds              = $tecIds
        requerimientosFuncionales   = $rfs
        requerimientosNoFuncionales = @()
        casosDeUso                  = @()
    }
    $r = Api "Post" "/api/proyectos" $body
    if ($r) { Write-Host "  [OK] Proyecto creado con id $($r.id)" -ForegroundColor Green }
}

function VerProyecto {
    $id = Read-Host "ID del proyecto"
    $r = Api "Get" "/api/proyectos/$id"
    if ($r) { Write-Host (Mostrar $r) }
}

function GenerarPlanificacion {
    $id = Read-Host "ID del proyecto"
    Write-Host "  Generando planificacion con la IA (puede tardar)..." -ForegroundColor DarkGray
    $r = Api "Post" "/api/proyectos/$id/planificacion"
    if ($r) {
        Write-Host "  [OK] Planificacion generada. Estado: $($r.estado)" -ForegroundColor Green
        Write-Host (Mostrar $r)
    }
}

function VerPlanificacion {
    $id = Read-Host "ID del proyecto"
    $r = Api "Get" "/api/proyectos/$id/planificacion"
    if ($r) { Write-Host (Mostrar $r) }
}

function ConfirmarPlanificacion {
    $id = Read-Host "ID del proyecto"
    $r = Api "Post" "/api/proyectos/$id/planificacion/confirmar"
    if ($r) { Write-Host "  [OK] Confirmacion: $($r.estado)" -ForegroundColor Green }
}

function AgregarHabilidad {
    Write-Host "  Catalogo: 1 Java, 2 Spring Boot, 3 Kotlin, 4 Jetpack Compose, 5 React, 6 Django, 7 Python, 8 PostgreSQL, 9 Analisis, 10 Requerimientos, 11 Gestion, 12 Pruebas" -ForegroundColor DarkGray
    $id = Read-Host "ID de habilidad"
    if (ApiSinCuerpo "Put" "/api/usuario/habilidades/$id") {
        Write-Host "  [OK] Habilidad agregada" -ForegroundColor Green
    }
}

function ListarNotificaciones {
    $r = Api "Get" "/api/notificaciones"
    if (-not $r) { Write-Host "  No hay notificaciones." -ForegroundColor Yellow; return }
    foreach ($n in @($r)) { Write-Host ("  [{0}] {1}" -f $n.id, $n.mensaje) -ForegroundColor Green }
}

function ListarTareas {
    $id = Read-Host "ID del proyecto"
    $r = Api "Get" "/api/proyectos/$id/tareas"
    if (-not $r) { Write-Host "  No hay tareas." -ForegroundColor Yellow; return }
    foreach ($t in @($r)) { Write-Host ("  [{0}] {1} - {2}" -f $t.id, $t.nombre, $t.estado) -ForegroundColor Green }
}

function InvitarIntegrante {
    $id = Read-Host "ID del proyecto"
    $email = Read-Host "Email del integrante a invitar"
    if (ApiSinCuerpo "Post" "/api/proyectos/$id/invitaciones" @{ email = $email }) {
        Write-Host "  [OK] Invitacion enviada a $email" -ForegroundColor Green
    }
}

function AceptarInvitacion {
    $r = Api "Get" "/api/invitaciones"
    if (-not $r) { Write-Host "  No tienes invitaciones pendientes." -ForegroundColor Yellow; return }
    $lista = @($r)
    Write-Host "  Invitaciones pendientes:" -ForegroundColor Cyan
    for ($i = 0; $i -lt $lista.Count; $i++) {
        Write-Host ("    [{0}] Proyecto '{1}' (integranteId {2})" -f $i, $lista[$i].proyectoNombre, $lista[$i].integranteId)
    }
    $idx = Read-Host "Indice a aceptar (o Enter para cancelar)"
    if ($idx -eq "") { return }
    $inv = $lista[[int]$idx]
    if (ApiSinCuerpo "Post" "/api/invitaciones/$($inv.integranteId)/aceptar") {
        Write-Host "  [OK] Invitacion aceptada" -ForegroundColor Green
    }
}

# ---------- menu principal ----------

function MostrarMenu {
    Write-Host ""
    Write-Host "===== COLAB - Consola de pruebas =====" -ForegroundColor Cyan
    if ($script:Email) { Write-Host "  Sesion : $($script:Email)" -ForegroundColor Green }
    else               { Write-Host "  Sesion : (sin autenticar)" -ForegroundColor Yellow }
    Write-Host "  URL    : $BaseUrl" -ForegroundColor DarkGray
    Write-Host ""
    Write-Host "  1. Registrarme"
    Write-Host "  2. Iniciar sesion"
    Write-Host "  3. Usar token manual"
    Write-Host "  ------------------------------"
    Write-Host "  4. Ver proveedor de IA"
    Write-Host "  5. Probar IA (demo)"
    Write-Host "  ------------------------------"
    Write-Host "  6. Listar mis proyectos"
    Write-Host "  7. Crear proyecto"
    Write-Host "  8. Ver proyecto (por id)"
    Write-Host "  ------------------------------"
    Write-Host "  9.  Generar planificacion (IA)"
    Write-Host "  10. Ver planificacion"
    Write-Host "  11. Confirmar planificacion"
    Write-Host "  ------------------------------"
    Write-Host "  12. Agregar habilidad a mi perfil"
    Write-Host "  13. Listar notificaciones"
    Write-Host "  14. Listar tareas de un proyecto"
    Write-Host "  ------------------------------"
    Write-Host "  15. Invitar integrante a un proyecto"
    Write-Host "  16. Aceptar invitacion"
    Write-Host "  ------------------------------"
    Write-Host "  0. Salir"
    Write-Host ""
}

do {
    MostrarMenu
    $op = Read-Host "Opcion"
    switch ($op) {
        "1"  { Registrarme }
        "2"  { Login }
        "3"  { UsarTokenManual }
        "4"  { VerProveedorIA }
        "5"  { DemoIA }
        "6"  { ListarProyectos }
        "7"  { CrearProyecto }
        "8"  { VerProyecto }
        "9"  { GenerarPlanificacion }
        "10" { VerPlanificacion }
        "11" { ConfirmarPlanificacion }
        "12" { AgregarHabilidad }
        "13" { ListarNotificaciones }
        "14" { ListarTareas }
        "15" { InvitarIntegrante }
        "16" { AceptarInvitacion }
        "0"  { Write-Host "Hasta luego."; break }
        default { Write-Host "  Opcion no valida." -ForegroundColor Red }
    }
} while ($op -ne "0")


