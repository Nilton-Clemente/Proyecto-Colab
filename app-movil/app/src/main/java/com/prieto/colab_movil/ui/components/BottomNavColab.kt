package com.prieto.colab_movil.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prieto.colab_movil.ui.theme.PurpleColab

@Composable
fun BottomNavColab(
    indiceSeleccionado: Int = 0,
    onTabSeleccionada: (Int) -> Unit = {}
) {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.navigationBarsPadding()
        ) {
            // Línea divisora superior
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFE2E8F0))
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ItemNavCustom(
                    titulo = "Tareas",
                    icono = Icons.Outlined.GridView,
                    seleccionado = indiceSeleccionado == 0,
                    onClick = { onTabSeleccionada(0) },
                    modifier = Modifier.weight(1f)
                )
                ItemNavCustom(
                    titulo = "Gestionar",
                    icono = Icons.Outlined.List,
                    seleccionado = indiceSeleccionado == 1,
                    onClick = { onTabSeleccionada(1) },
                    modifier = Modifier.weight(1f)
                )
                ItemNavCustom(
                    titulo = "Calendario",
                    icono = Icons.Outlined.Schedule,
                    seleccionado = indiceSeleccionado == 2,
                    onClick = { onTabSeleccionada(2) },
                    modifier = Modifier.weight(1f)
                )
                ItemNavCustom(
                    titulo = "Perfil",
                    icono = Icons.Outlined.Person,
                    seleccionado = indiceSeleccionado == 3,
                    onClick = { onTabSeleccionada(3) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ItemNavCustom(
    titulo: String,
    icono: ImageVector,
    seleccionado: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colorContenido = if (seleccionado) PurpleColab else Color(0xFF64748B)

    Column(
        modifier = modifier
            .fillMaxHeight()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .width(36.dp)
                .height(3.dp)
                .background(
                    color = if (seleccionado) PurpleColab else Color.Transparent,
                    shape = RoundedCornerShape(bottomStart = 2.dp, bottomEnd = 2.dp)
                )
        )

        Spacer(modifier = Modifier.height(2.dp))

        Icon(
            imageVector = icono,
            contentDescription = titulo,
            tint = colorContenido,
            modifier = Modifier.size(22.dp)
        )

        Text(
            text = titulo,
            fontSize = 11.sp,
            fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal,
            color = colorContenido
        )

        Spacer(modifier = Modifier.height(6.dp))
    }
}