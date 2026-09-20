# ============================================================
#  Prueba de conectividad con "La BestIA" (OpenWebUI de TECSUP)
#  desde la consola, SIN pasar por el backend de Spring Boot.
#
#  Uso (desde la red del instituto):
#    .\test-ia-conexion.ps1 -Url "http://192.168.17.11:3000" -Token "TU_TOKEN" -Model "Qwen/Qwen3.6-35B-A3B-FP8"
#
#  También lee las variables de entorno OPENWEBUI_URL, USER_API_KEY y MODEL
#  si no pasas los parámetros.
# ============================================================

param(
    [string]$Url = $env:OPENWEBUI_URL,
    [string]$Token = $env:USER_API_KEY,
    [string]$Model = $env:MODEL
)

if (-not $Url)   { $Url   = "http://192.168.17.11:3000" }
if (-not $Model) { $Model = "Qwen/Qwen3.6-35B-A3B-FP8" }
if (-not $Token) { $Token = Read-Host "Ingresa tu TOKEN" }

$headers = @{
    "Authorization" = "Bearer $Token"
    "Content-Type"  = "application/json"
}

Write-Host ""
Write-Host "1) Conectividad + token:  GET $Url/api/v1/models" -ForegroundColor Cyan
try {
    $models = Invoke-RestMethod -Uri "$Url/api/v1/models" -Headers $headers -Method Get -TimeoutSec 20
    $nombres = ($models.data | ForEach-Object { $_.id }) -join ", "
    Write-Host "   OK. Modelos disponibles: $nombres" -ForegroundColor Green
}
catch {
    Write-Host "   FALLO: no se pudo conectar o el token es invalido." -ForegroundColor Red
    Write-Host ("   Detalle: " + $_.Exception.Message) -ForegroundColor Red
    exit 1
}

Write-Host "2) Generacion de texto:   POST $Url/api/chat/completions" -ForegroundColor Cyan
$cuerpo = @{
    model    = $Model
    stream   = $false
    messages = @(
        @{ role = "system"; content = "Eres un asistente de pruebas. Responde breve." },
        @{ role = "user";   content = "Responde con una sola frase: funciona esta conexion?" }
    )
} | ConvertTo-Json -Depth 5

try {
    $resp = Invoke-RestMethod -Uri "$Url/api/chat/completions" -Headers $headers -Method Post -Body $cuerpo -TimeoutSec 120
    Write-Host "   OK. Respuesta del modelo:" -ForegroundColor Green
    Write-Host ("   " + $resp.choices[0].message.content) -ForegroundColor White
}
catch {
    Write-Host "   FALLO en la generacion." -ForegroundColor Red
    Write-Host ("   Detalle: " + $_.Exception.Message) -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Conectividad OK: ya puedes levantar el backend con IA_PROVIDER=real." -ForegroundColor Green
