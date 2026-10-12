package com.prieto.colab_movil.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prieto.colab_movil.data.model.ResumenGestion
import com.prieto.colab_movil.data.model.TareaGestion
import com.prieto.colab_movil.ui.theme.PurpleColab

@Composable
fun HeaderGestion() {
    Column(modifier = Modifier.padding(top = 8.dp)) {
        Text("Administrar tareas", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
        Text("Actualiza responsables, plazos y estados.", color = Color(0xFF64748B), fontSize = 13.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PurpleColab),
            shape = RoundedCornerShape(10.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Nueva tarea", modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Nueva tarea", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}

@Composable
fun TarjetasResumenGestion(resumen: ResumenGestion) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ItemMetrica(titulo = "TOTAL", valor = "${resumen.total}", colorValor = Color(0xFF0F172A), modifier = Modifier.weight(1f))
        ItemMetrica(titulo = "BLOQUEADAS", valor = "${resumen.bloqueadas}", colorValor = Color(0xFFD93025), modifier = Modifier.weight(1f))
        ItemMetrica(titulo = "VENCEN PRONTO", valor = "${resumen.vencenPronto}", colorValor = Color(0xFFD97706), modifier = Modifier.weight(1f))
    }
}

@Composable
fun ItemMetrica(titulo: String, valor: String, colorValor: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 10.dp)
        ) {
            Text(
                text = titulo,
                color = Color(0xFF64748B),
                fontSize = 8.5.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(valor, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = colorValor)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuscadorYFiltrosGestion() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Código, título o responsable", fontSize = 12.sp, color = Color.Gray) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                unfocusedBorderColor = Color(0xFFE2E8F0)
            ),
            singleLine = true
        )

        Text("FILTROS OPERATIVOS", color = Color(0xFF64748B), fontSize = 10.sp, fontWeight = FontWeight.Bold)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChipItem("Etapa: Todas")
            FilterChipItem("Estado: Todos")
            FilterChipItem("Prioridad: Todas")
            FilterChipItem("Responsable: Todos")
        }
    }
}

@Composable
fun FilterChipItem(texto: String) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 0.5.dp
    ) {
        Text(
            text = texto,
            fontSize = 11.sp,
            color = Color(0xFF334155),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun SubheaderContadorVisual() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("9 tareas encontradas", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF0F172A))
        Text("Ordenar: vencimiento ↓", fontSize = 11.sp, color = PurpleColab, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun ItemTareaGestionCard(tarea: TareaGestion) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(tarea.colorBarraIzquierda)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "#${tarea.id} • ${tarea.prioridad.name}",
                        color = PurpleColab,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        imageVector = Icons.Default.MoreHoriz,
                        contentDescription = "Opciones",
                        tint = Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = tarea.titulo,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFF0F172A)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Responsable: ${tarea.responsable}", color = Color(0xFF64748B), fontSize = 11.sp)
                    Text(tarea.fechaVencimiento, color = Color(0xFF64748B), fontSize = 11.sp)
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(tarea.dependencia, color = Color(0xFF94A3B8), fontSize = 10.sp)

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = tarea.colorFondoBadge,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = tarea.estado.name.replace("_", " "),
                            color = tarea.colorTextoBadge,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Text(
                        text = tarea.textoAccion,
                        color = PurpleColab,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { }
                    )
                }
            }
        }
    }
}