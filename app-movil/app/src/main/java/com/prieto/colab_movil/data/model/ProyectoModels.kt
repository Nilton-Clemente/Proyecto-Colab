package com.prieto.colab_movil.data.model

import androidx.compose.ui.graphics.Color

enum class Prioridad { ALTA, MEDIA, BAJA }

data class Tarea(
    val id: String,
    val prioridad: Prioridad,
    val titulo: String,
    val descripcion: String,
    val estadoBadgeText: String,
    val fechaOEstadoText: String,
    val colorBarraIzquierda: Color,
    val colorFondoTarjeta: Color
)

data class EtapaProyecto(
    val numero: Int,
    val nombre: String,
    val totalTareas: Int,
    val porcentajeAvance: Int,
    val colorNumeroBadge: Color,
    val colorFondoBadge: Color,
    val tareas: List<Tarea>
)