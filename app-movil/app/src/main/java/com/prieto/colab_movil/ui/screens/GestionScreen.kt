package com.prieto.colab_movil.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prieto.colab_movil.ui.components.*
import com.prieto.colab_movil.ui.viewmodel.GestionViewModel

@Composable
fun GestionScreen(viewModel: GestionViewModel = viewModel()) {
    val resumen by viewModel.resumen.collectAsState()
    val tareas by viewModel.tareas.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { HeaderGestion() }
        item { TarjetasResumenGestion(resumen) }
        item { BuscadorYFiltrosGestion() }
        item { SubheaderContadorVisual() }

        items(tareas) { tarea ->
            ItemTareaGestionCard(tarea = tarea)
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}