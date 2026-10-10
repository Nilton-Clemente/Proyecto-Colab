package com.prieto.colab_movil.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prieto.colab_movil.ui.components.*
import com.prieto.colab_movil.ui.theme.PurpleColab
import com.prieto.colab_movil.ui.viewmodel.ColabViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColabScreen(viewModel: ColabViewModel = viewModel()) {
    val etapas by viewModel.etapas.collectAsState()

    Scaffold(
        topBar = { TopBarColab() },
        bottomBar = { BottomNavColab() }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { HeaderProyecto() }
            item { TarjetaAvanceGlobal() }
            item { FiltrosYAlerta() }

            items(etapas) { etapa ->
                SeccionEtapa(etapa = etapa)
            }

            item {
                TextButton(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ver todas las tareas →", color = PurpleColab, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}