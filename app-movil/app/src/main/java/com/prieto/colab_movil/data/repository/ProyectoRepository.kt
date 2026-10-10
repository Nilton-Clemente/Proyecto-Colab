package com.prieto.colab_movil.data.repository

import androidx.compose.ui.graphics.Color
import com.prieto.colab_movil.data.model.EtapaProyecto
import com.prieto.colab_movil.data.model.Prioridad
import com.prieto.colab_movil.data.model.Tarea

class ProyectoRepository {
    fun obtenerEtapas(): List<EtapaProyecto> {
        val greenBar = Color(0xFF10B981)
        val greenBg = Color(0xFFF0FDF4)
        val purpleBar = Color(0xFF4F46E5)
        val purpleBg = Color(0xFFEEF2FF)
        val redBar = Color(0xFFEF4444)
        val redBg = Color(0xFFFEF2F2)

        return listOf(
            EtapaProyecto(
                numero = 1,
                nombre = "Diseño",
                totalTareas = 2,
                porcentajeAvance = 50,
                colorNumeroBadge = Color(0xFF047857),
                colorFondoBadge = Color(0xFFD1FAE5),
                tareas = listOf(
                    Tarea("101", Prioridad.ALTA, "Arquitectura de software", "Diagramas C4 y componentes", "COMPLETADA", "Vence 10 oct.", greenBar, greenBg),
                    Tarea("102", Prioridad.MEDIA, "Prototipado UI/UX en Figma", "Wireframes de visualización y gestión", "COMPLETADA", "Vence 10 oct.", greenBar, greenBg)
                )
            ),
            EtapaProyecto(
                numero = 2,
                nombre = "Desarrollo",
                totalTareas = 2,
                porcentajeAvance = 25,
                colorNumeroBadge = Color(0xFF4338CA),
                colorFondoBadge = Color(0xFFE0E7FF),
                tareas = listOf(
                    Tarea("104", Prioridad.ALTA, "Interfaz de tareas", "Pantallas Android y navegación", "COMPLETADA", "En curso", purpleBar, purpleBg),
                    Tarea("105", Prioridad.MEDIA, "API de tareas", "Servicios y persistencia de datos", "COMPLETADA", "En curso", purpleBar, purpleBg)
                )
            ),
            EtapaProyecto(
                numero = 3,
                nombre = "Pruebas",
                totalTareas = 1,
                porcentajeAvance = 0,
                colorNumeroBadge = Color(0xFFB45309),
                colorFondoBadge = Color(0xFFFEF3C7),
                tareas = listOf(
                    Tarea("108", Prioridad.MEDIA, "Pruebas de integración", "Validación de flujos principales", "COMPLETADA", "Revisar bloqueo", redBar, redBg)
                )
            )
        )
    }
}