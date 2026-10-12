package com.prieto.colab_movil.data.repository

import androidx.compose.ui.graphics.Color
import com.prieto.colab_movil.data.model.EstadoTarea
import com.prieto.colab_movil.data.model.Prioridad
import com.prieto.colab_movil.data.model.ResumenGestion
import com.prieto.colab_movil.data.model.TareaGestion

class GestionRepository {

    fun obtenerResumen(): ResumenGestion {
        return ResumenGestion(total = 9, bloqueadas = 2, vencenPronto = 3)
    }

    fun obtenerTareasGestion(): List<TareaGestion> {
        val greenBar = Color(0xFF10B981)
        val purpleBar = Color(0xFF4F46E5)
        val redBar = Color(0xFFEF4444)
        val orangeBar = Color(0xFFF59E0B)

        return listOf(
            TareaGestion(
                id = "101",
                prioridad = Prioridad.ALTA,
                titulo = "Arquitectura de software",
                responsable = "Ana Pérez",
                fechaVencimiento = "Vence: 10 oct.",
                dependencia = "Dep.: Ninguna",
                estado = EstadoTarea.COMPLETADA,
                colorBarraIzquierda = greenBar,
                colorFondoBadge = Color(0xFFE6F4EA),
                colorTextoBadge = Color(0xFF0F9D58)
            ),
            TareaGestion(
                id = "104",
                prioridad = Prioridad.ALTA,
                titulo = "Interfaz de tareas",
                responsable = "Nilton",
                fechaVencimiento = "Vence: 12 oct.",
                dependencia = "Dep.: #101 Arquitectura",
                estado = EstadoTarea.EN_PROGRESO,
                colorBarraIzquierda = purpleBar,
                colorFondoBadge = Color(0xFFEEF2FF),
                colorTextoBadge = Color(0xFF4F46E5)
            ),
            TareaGestion(
                id = "108",
                prioridad = Prioridad.MEDIA,
                titulo = "Pruebas de integración",
                responsable = "Alexis",
                fechaVencimiento = "Vence: 14 oct.",
                dependencia = "Dep.: #104 Interfaz",
                estado = EstadoTarea.BLOQUEADA,
                colorBarraIzquierda = redBar,
                colorFondoBadge = Color(0xFFFCE8E6),
                colorTextoBadge = Color(0xFFD93025),
                textoAccion = "Resolver →"
            ),
            TareaGestion(
                id = "105",
                prioridad = Prioridad.MEDIA,
                titulo = "API de tareas",
                responsable = "Piero",
                fechaVencimiento = "Vence: 13 oct.",
                dependencia = "Dep.: #101 Arquitectura",
                estado = EstadoTarea.EN_PROGRESO,
                colorBarraIzquierda = purpleBar,
                colorFondoBadge = Color(0xFFEEF2FF),
                colorTextoBadge = Color(0xFF4F46E5)
            ),
            TareaGestion(
                id = "109",
                prioridad = Prioridad.BAJA,
                titulo = "Documentación técnica",
                responsable = "Sin asignar",
                fechaVencimiento = "Vence: 16 oct.",
                dependencia = "Dep.: #105 API de tareas",
                estado = EstadoTarea.PENDIENTE,
                colorBarraIzquierda = orangeBar,
                colorFondoBadge = Color(0xFFFEF3C7),
                colorTextoBadge = Color(0xFFB45309)
            )
        )
    }
}