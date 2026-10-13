#!/usr/bin/env bash
# ============================================================
#  Colab - Consola de pruebas (CLI) - version bash
#  Compatible con macOS / Linux (sin necesidad de PowerShell).
#
#  Uso:
#    cd backend-usuario
#    chmod +x consola-colab.sh
#    ./consola-colab.sh
#
#  Requisitos:
#    - backend corriendo en http://localhost:8081
#    - curl (viene por defecto en macOS/Linux)
#    - jq   ->  brew install jq   (o apt-get install jq)
# ============================================================

BASE_URL="${1:-http://localhost:8081}"
TOKEN=""
EMAIL=""

# ---- colores (ANSI) ----
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
CYAN='\033[0;36m'
WHITE='\033[0;37m'
GRAY='\033[0;90m'
NC='\033[0m'

# ---- utilidades ----

verde() { while IFS= read -r line; do echo -e "${GREEN}${line}${NC}"; done; }

leer_clave() {
    local pass=""
    read -r -s -p "Contrasena: " pass
    echo ""
    printf '%s' "$pass"
}

leer_con_default() {
    local prompt="$1" default="$2" val=""
    read -r -p "$prompt [$default]: " val
    if [ -z "$val" ]; then printf '%s' "$default"; else printf '%s' "$val"; fi
}

fecha_mas_60() {
    date -v+60d +%Y-%m-%d 2>/dev/null || date -d '+60 days' +%Y-%m-%d 2>/dev/null || date +%Y-%m-%d
}

api() {
    local metodo="$1" ruta="$2" body="${3:-}"
    local tmpfile status detalle
    local -a args
    tmpfile=$(mktemp)
    args=(-sS -X "$metodo" "$BASE_URL$ruta" -w '%{http_code}' -o "$tmpfile")
    if [ -n "$TOKEN" ]; then args+=(-H "Authorization: Bearer $TOKEN"); fi
    if [ -n "$body" ]; then args+=(-H "Content-Type: application/json" -d "$body"); fi
    status=$(curl "${args[@]}")
    if [ "$status" -ge 200 ] && [ "$status" -lt 300 ]; then
        cat "$tmpfile"
        rm -f "$tmpfile"
        return 0
    fi
    echo -e "  ${RED}[ERROR $status]${NC}" >&2
    detalle=$(cat "$tmpfile" 2>/dev/null)
    if [ -n "$detalle" ]; then echo -e "  ${YELLOW}Detalle: $detalle${NC}" >&2; fi
    rm -f "$tmpfile"
    return 1
}

api_sin_cuerpo() {
    local metodo="$1" ruta="$2" body="${3:-}"
    local status
    local -a args
    args=(-sS -o /dev/null -w '%{http_code}' -X "$metodo" "$BASE_URL$ruta")
    if [ -n "$TOKEN" ]; then args+=(-H "Authorization: Bearer $TOKEN"); fi
    if [ -n "$body" ]; then args+=(-H "Content-Type: application/json" -d "$body"); fi
    status=$(curl "${args[@]}")
    if [ "$status" -ge 200 ] && [ "$status" -lt 300 ]; then return 0; fi
    echo -e "  ${RED}[ERROR $status]${NC}" >&2
    return 1
}

mostrar() { printf '%s' "$1" | jq .; }

abrir_navegador() {
    local url="$1"
    if command -v open >/dev/null 2>&1; then
        open "$url"                                   # macOS
    elif command -v xdg-open >/dev/null 2>&1; then
        xdg-open "$url"                               # Linux
    else
        echo -e "  ${YELLOW}Abre manualmente esta URL: $url${NC}" >&2
    fi
}

# ---- acciones ----

registrarme() {
    local nombre email pass body r
    read -r -p "Nombre: " nombre
    read -r -p "Email: " email
    pass=$(leer_clave)
    body=$(jq -n --arg e "$email" --arg c "$pass" --arg n "$nombre" '{email:$e, contrasena:$c, nombre:$n}')
    r=$(api "POST" "/api/auth/registro" "$body") || return
    TOKEN=$(printf '%s' "$r" | jq -r '.token')
    EMAIL="$email"
    echo -e "  ${GREEN}[OK] Registrado y autenticado como $email${NC}"
}

login() {
    local email pass body r
    read -r -p "Email: " email
    pass=$(leer_clave)
    body=$(jq -n --arg e "$email" --arg c "$pass" '{email:$e, contrasena:$c}')
    r=$(api "POST" "/api/auth/login" "$body") || return
    TOKEN=$(printf '%s' "$r" | jq -r '.token')
    EMAIL="$email"
    echo -e "  ${GREEN}[OK] Autenticado como $email${NC}"
}

usar_token_manual() {
    local t
    read -r -p "Token JWT: " t
    if [ -n "$t" ]; then TOKEN="$t"; echo -e "  ${GREEN}[OK] Token cargado${NC}"; fi
}

ver_proveedor_ia() {
    local r
    r=$(api "GET" "/api/ia/proveedor") || return
    echo -e "  ${CYAN}Proveedor: $(printf '%s' "$r" | jq -r '.proveedor')${NC}"
}

demo_ia() {
    local m body r
    read -r -p "Mensaje para la IA: " m
    body=$(jq -n --arg m "$m" '{mensaje:$m}')
    r=$(api "POST" "/api/ia/demo" "$body") || return
    echo ""
    echo -e "  ${WHITE}Respuesta: $(printf '%s' "$r" | jq -r '.respuesta')${NC}"
    echo ""
}

listar_proyectos() {
    local r out
    r=$(api "GET" "/api/proyectos") || return
    out=$(printf '%s' "$r" | jq -r '.[] | "  [\(.id)] \(.nombre)"' 2>/dev/null)
    if [ -z "$out" ]; then echo -e "  ${YELLOW}No hay proyectos (o error).${NC}"; return; fi
    printf '%s\n' "$out" | verde
}

crear_proyecto() {
    echo -e "  ${GRAY}(deja el campo vacio y pulsa Enter para usar el valor por defecto)${NC}"
    local nombre objetivo fecha tec rfs_txt tec_ids rfs body r
    nombre=$(leer_con_default "Nombre" "Proyecto Colab Demo")
    objetivo=$(leer_con_default "Objetivo general" "Desarrollar una plataforma de colaboracion estudiantil para la gestion de proyectos academicos")
    fecha=$(leer_con_default "Fecha de entrega (YYYY-MM-DD)" "$(fecha_mas_60)")

    echo -e "  ${GRAY}Catalogo: 1 Java, 2 Spring Boot, 3 Kotlin, 4 Jetpack Compose, 5 React, 6 Django, 7 Python, 8 PostgreSQL, 9 Flutter, 10 Node.js${NC}"
    tec=$(leer_con_default "Tecnologias (IDs separados por coma)" "1,2,8")
    rfs_txt=$(leer_con_default "Requerimientos funcionales (separados por ';')" "Registro e inicio de sesion de usuarios;Gestion de proyectos;Generacion de planificacion con IA;Gestion de tareas")

    tec_ids=$(printf '%s' "$tec" | jq -R -c 'split(",") | map(gsub("^ +| +$";"") | tonumber)')
    rfs=$(printf '%s' "$rfs_txt" | jq -R -c 'split(";") | map(gsub("^ +| +$";"") | select(length>0) | {descripcion: .})')

    body=$(jq -n --arg nombre "$nombre" --arg objetivo "$objetivo" --arg fecha "$fecha" \
        --argjson tecnologiasIds "$tec_ids" --argjson requerimientosFuncionales "$rfs" \
        '{nombre:$nombre, objetivoGeneral:$objetivo, fechaEntrega:$fecha, tecnologiasIds:$tecnologiasIds, requerimientosFuncionales:$requerimientosFuncionales, requerimientosNoFuncionales:[], casosDeUso:[]}')

    r=$(api "POST" "/api/proyectos" "$body") || return
    echo -e "  ${GREEN}[OK] Proyecto creado con id $(printf '%s' "$r" | jq -r '.id')${NC}"
}

ver_proyecto() {
    local id r
    read -r -p "ID del proyecto: " id
    r=$(api "GET" "/api/proyectos/$id") || return
    mostrar "$r"
}

generar_planificacion() {
    local id r
    read -r -p "ID del proyecto: " id
    echo -e "  ${GRAY}Generando planificacion con la IA (puede tardar)...${NC}"
    r=$(api "POST" "/api/proyectos/$id/planificacion") || return
    echo -e "  ${GREEN}[OK] Planificacion generada. Estado: $(printf '%s' "$r" | jq -r '.estado')${NC}"
    mostrar "$r"
}

ver_planificacion() {
    local id r
    read -r -p "ID del proyecto: " id
    r=$(api "GET" "/api/proyectos/$id/planificacion") || return
    mostrar "$r"
}

confirmar_planificacion() {
    local id r
    read -r -p "ID del proyecto: " id
    r=$(api "POST" "/api/proyectos/$id/planificacion/confirmar") || return
    echo -e "  ${GREEN}[OK] Confirmacion: $(printf '%s' "$r" | jq -r '.estado')${NC}"
}

agregar_habilidad() {
    echo -e "  ${GRAY}Catalogo: 1 Java, 2 Spring Boot, 3 Kotlin, 4 Jetpack Compose, 5 React, 6 Django, 7 Python, 8 PostgreSQL, 9 Analisis, 10 Requerimientos, 11 Gestion, 12 Pruebas${NC}"
    local id
    read -r -p "ID de habilidad: " id
    if api_sin_cuerpo "PUT" "/api/usuario/habilidades/$id"; then
        echo -e "  ${GREEN}[OK] Habilidad agregada${NC}"
    fi
}

listar_notificaciones() {
    local r out
    r=$(api "GET" "/api/notificaciones") || return
    out=$(printf '%s' "$r" | jq -r '.[] | "  [\(.id)] \(.mensaje)"' 2>/dev/null)
    if [ -z "$out" ]; then echo -e "  ${YELLOW}No hay notificaciones.${NC}"; return; fi
    printf '%s\n' "$out" | verde
}

listar_tareas() {
    local id r out
    read -r -p "ID del proyecto: " id
    r=$(api "GET" "/api/proyectos/$id/tareas") || return
    out=$(printf '%s' "$r" | jq -r '.[] | "  [\(.id)] \(.nombre) - \(.estado)"' 2>/dev/null)
    if [ -z "$out" ]; then echo -e "  ${YELLOW}No hay tareas.${NC}"; return; fi
    printf '%s\n' "$out" | verde
}

invitar_integrante() {
    local id email body
    read -r -p "ID del proyecto: " id
    read -r -p "Email del integrante a invitar: " email
    body=$(jq -n --arg e "$email" '{email:$e}')
    if api_sin_cuerpo "POST" "/api/proyectos/$id/invitaciones" "$body"; then
        echo -e "  ${GREEN}[OK] Invitacion enviada a $email${NC}"
    fi
}

aceptar_invitacion() {
    local r count idx integrante_id
    r=$(api "GET" "/api/invitaciones") || return
    count=$(printf '%s' "$r" | jq 'length' 2>/dev/null)
    if [ -z "$count" ] || [ "$count" -eq 0 ]; then
        echo -e "  ${YELLOW}No tienes invitaciones pendientes.${NC}"
        return
    fi
    echo -e "  ${CYAN}Invitaciones pendientes:${NC}"
    printf '%s' "$r" | jq -r 'to_entries[] | "    [\(.key)] \(.value.proyectoNombre) (integranteId \(.value.integranteId))"'
    read -r -p "Indice a aceptar (o Enter para cancelar): " idx
    if [ -z "$idx" ]; then return; fi
    integrante_id=$(printf '%s' "$r" | jq -r ".[$idx].integranteId")
    if api_sin_cuerpo "POST" "/api/invitaciones/$integrante_id/aceptar"; then
        echo -e "  ${GREEN}[OK] Invitacion aceptada${NC}"
    fi
}

abrir_tareas_web() {
    local tab="$1" id url
    if [ -z "$TOKEN" ]; then echo -e "  ${YELLOW}Primero inicia sesion (opcion 2).${NC}"; return; fi
    read -r -p "ID del proyecto: " id
    if [ -z "$id" ]; then echo -e "  ${YELLOW}ID requerido.${NC}"; return; fi
    url="$BASE_URL/web-tareas.html?token=$TOKEN&proyectoId=$id&tab=$tab"
    abrir_navegador "$url"
    echo -e "  ${GREEN}[OK] Interfaz web abierta en el navegador (pestana: $tab).${NC}"
}

# ---- menu principal ----

mostrar_menu() {
    echo ""
    echo -e "${CYAN}===== COLAB - Consola de pruebas =====${NC}"
    if [ -n "$EMAIL" ]; then
        echo -e "  ${GREEN}Sesion : $EMAIL${NC}"
    else
        echo -e "  ${YELLOW}Sesion : (sin autenticar)${NC}"
    fi
    echo -e "  ${GRAY}URL    : $BASE_URL${NC}"
    echo ""
    echo "  1. Registrarme"
    echo "  2. Iniciar sesion"
    echo "  3. Usar token manual"
    echo "  ------------------------------"
    echo "  4. Ver proveedor de IA"
    echo "  5. Probar IA (demo)"
    echo "  ------------------------------"
    echo "  6. Listar mis proyectos"
    echo "  7. Crear proyecto"
    echo "  8. Ver proyecto (por id)"
    echo "  ------------------------------"
    echo "  9.  Generar planificacion (IA)"
    echo "  10. Ver planificacion"
    echo "  11. Confirmar planificacion"
    echo "  ------------------------------"
    echo "  12. Agregar habilidad a mi perfil"
    echo "  13. Listar notificaciones"
    echo "  14. Listar tareas de un proyecto"
    echo "  ------------------------------"
    echo "  15. Invitar integrante a un proyecto"
    echo "  16. Aceptar invitacion"
    echo "  ------------------------------"
    echo "  17. Ver tareas (interfaz web)"
    echo "  18. Gestionar tareas (interfaz web)"
    echo "  ------------------------------"
    echo "  0. Salir"
    echo ""
}

# ---- inicio ----

if ! command -v curl >/dev/null 2>&1; then
    echo "Error: 'curl' no esta instalado." >&2
    exit 1
fi
if ! command -v jq >/dev/null 2>&1; then
    echo "Error: 'jq' no esta instalado. Instalalo con: brew install jq" >&2
    exit 1
fi

while true; do
    mostrar_menu
    read -r -p "Opcion: " op
    case "$op" in
        1)  registrarme ;;
        2)  login ;;
        3)  usar_token_manual ;;
        4)  ver_proveedor_ia ;;
        5)  demo_ia ;;
        6)  listar_proyectos ;;
        7)  crear_proyecto ;;
        8)  ver_proyecto ;;
        9)  generar_planificacion ;;
        10) ver_planificacion ;;
        11) confirmar_planificacion ;;
        12) agregar_habilidad ;;
        13) listar_notificaciones ;;
        14) listar_tareas ;;
        15) invitar_integrante ;;
        16) aceptar_invitacion ;;
        17) abrir_tareas_web "visualizacion" ;;
        18) abrir_tareas_web "gestion" ;;
        0)  echo "Hasta luego."; break ;;
        *)  echo -e "  ${RED}Opcion no valida.${NC}" ;;
    esac
done