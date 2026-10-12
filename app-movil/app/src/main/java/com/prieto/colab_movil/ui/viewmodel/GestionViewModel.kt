package com.prieto.colab_movil.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.prieto.colab_movil.data.model.ResumenGestion
import com.prieto.colab_movil.data.model.TareaGestion
import com.prieto.colab_movil.data.repository.GestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GestionViewModel(
    private val repository: GestionRepository = GestionRepository()
) : ViewModel() {

    private val _resumen = MutableStateFlow(repository.obtenerResumen())
    val resumen: StateFlow<ResumenGestion> = _resumen.asStateFlow()

    private val _tareas = MutableStateFlow<List<TareaGestion>>(emptyList())
    val tareas: StateFlow<List<TareaGestion>> = _tareas.asStateFlow()

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        _tareas.value = repository.obtenerTareasGestion()
    }
}