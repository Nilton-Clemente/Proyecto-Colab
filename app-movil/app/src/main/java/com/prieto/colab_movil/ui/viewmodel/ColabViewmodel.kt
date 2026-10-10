package com.prieto.colab_movil.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.prieto.colab_movil.data.model.EtapaProyecto
import com.prieto.colab_movil.data.repository.ProyectoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ColabViewModel(
    private val repository: ProyectoRepository = ProyectoRepository()
) : ViewModel() {

    private val _etapas = MutableStateFlow<List<EtapaProyecto>>(emptyList())
    val etapas: StateFlow<List<EtapaProyecto>> = _etapas.asStateFlow()

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        _etapas.value = repository.obtenerEtapas()
    }
}