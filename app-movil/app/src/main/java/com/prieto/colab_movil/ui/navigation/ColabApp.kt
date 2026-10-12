package com.prieto.colab_movil.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.prieto.colab_movil.ui.components.BottomNavColab
import com.prieto.colab_movil.ui.components.TopBarColab
import com.prieto.colab_movil.ui.screens.ColabScreen
import com.prieto.colab_movil.ui.screens.GestionScreen

@Composable
fun ColabApp() {
    var tabSeleccionada by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = { TopBarColab() },
        bottomBar = {
            BottomNavColab(
                indiceSeleccionado = tabSeleccionada,
                onTabSeleccionada = { nuevoIndice ->
                    tabSeleccionada = nuevoIndice
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (tabSeleccionada) {
                0 -> ColabScreen(onGestionarTodasLasTareasClick = { tabSeleccionada = 1 })
                1 -> GestionScreen()
            }
        }
    }
}