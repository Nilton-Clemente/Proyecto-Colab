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
import com.prieto.colab_movil.ui.viewmodel.ColabViewModel

@Composable
fun ColabScreen(
    viewModel: ColabViewModel = viewModel(),
    onGestionarTodasLasTareasClick: () -> Unit = {}
) {
    val etapas by viewModel.etapas.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { HeaderProyecto() }
        item { TarjetaAvanceGlobal() }
        item { FiltrosYAlerta() }

        items(etapas) { etapa ->
            SeccionEtapa(etapa = etapa)
        }
        item {
            BotonGestionarTodasLasTareas(onClick = onGestionarTodasLasTareasClick)
        }
    }
}