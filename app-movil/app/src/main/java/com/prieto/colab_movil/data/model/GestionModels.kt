package com.prieto.colab_movil.data.model

import androidx.compose.ui.graphics.Color

enum class EstadoTarea { COMPLETADA, EN_PROGRESO, BLOQUEADA, PENDIENTE }

data class TareaGestion(
    val id: String,
    val prioridad: Prioridad,
    val titulo: String,
    val responsable: String,
    val fechaVencimiento: String,
    val dependencia: String,
    val estado: EstadoTarea,
    val colorBarraIzquierda: Color,
    val colorFondoBadge: Color,
    val colorTextoBadge: Color,
    val textoAccion: String = "Editar →"
)

data class ResumenGestion(
    val total: Int,
    val bloqueadas: Int,
    val vencenPronto: Int
)