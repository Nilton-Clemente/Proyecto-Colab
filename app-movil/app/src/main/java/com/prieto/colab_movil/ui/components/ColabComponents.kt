package com.prieto.colab_movil.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prieto.colab_movil.data.model.EtapaProyecto
import com.prieto.colab_movil.data.model.Tarea
import com.prieto.colab_movil.ui.theme.*

@Composable
fun TopBarColab() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkHeaderBg)
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(PurpleColab),
                contentAlignment = Alignment.Center
            ) {
                Text("C", color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text("Colab", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
        Text("SISTEMA COLAB", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(PurpleColab),
            contentAlignment = Alignment.Center
        ) {
            Text("AP", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun HeaderProyecto() {
    Column(modifier = Modifier.padding(top = 8.dp)) {
        Text("Proyecto académico", color = Color(0xFF64748B), fontSize = 12.sp)
        Text("Sistema Colab v1.0", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
        Text("Ingeniería de Software II", color = Color(0xFF64748B), fontSize = 14.sp)
        Spacer(modifier = Modifier.height(6.dp))
        Surface(
            color = LightBlueBg,
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                "EN DESARROLLO",
                color = PurpleColab,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Avance del proyecto", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
        Text("Explora el progreso y las dependencias por etapa.", color = Color(0xFF64748B), fontSize = 13.sp)
    }
}

@Composable
fun TarjetaAvanceGlobal() {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("AVANCE GLOBAL", color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("33%", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text("3 de 9 tareas completadas", color = Color.Gray, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { 0.33f },
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                color = PurpleColab,
                trackColor = Color(0xFFE0E0E0)
            )
        }
    }
}

@Composable
fun FiltrosYAlerta() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("VISTA DEL PROYECTO", color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = PurpleColab),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) { Text("Todas • 9", fontSize = 12.sp) }

            OutlinedButton(
                onClick = {},
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) { Text("Mis tareas • 3", fontSize = 12.sp, color = Color.Gray) }

            OutlinedButton(
                onClick = {},
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) { Text("Bloqueadas • 2", fontSize = 12.sp, color = Color.Gray) }
        }
        Surface(
            color = LightRedBg,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "● 1 tarea bloqueada por una dependencia",
                color = RedAlertText,
                fontSize = 11.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun ItemTarea(tarea: Tarea) {
    Card(
        colors = CardDefaults.cardColors(containerColor = tarea.colorFondoTarjeta),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(tarea.colorBarraIzquierda)
            )

            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "#${tarea.id} • ${tarea.prioridad.name}",
                    color = PurpleColab,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = tarea.titulo,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFF1E293B)
                )
                Text(
                    text = tarea.descripcion,
                    color = Color(0xFF64748B),
                    fontSize = 11.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = tarea.estadoBadgeText,
                            color = DarkGreenText,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                    Text(
                        text = tarea.fechaOEstadoText,
                        color = Color(0xFF64748B),
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

@Composable
fun SeccionEtapa(etapa: EtapaProyecto) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(etapa.colorFondoBadge),
                    contentAlignment = Alignment.Center
                ) {
                    Text("${etapa.numero}", color = etapa.colorNumeroBadge, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(etapa.nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Text("${etapa.totalTareas} tareas • ${etapa.porcentajeAvance}%", color = etapa.colorNumeroBadge, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }

        etapa.tareas.forEach { tarea ->
            ItemTarea(tarea = tarea)
        }
    }
}

@Composable
fun BotonGestionarTodasLasTareas(onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        TextButton(
            onClick = onClick,
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(
                text = "Gestionar todas las tareas  →",
                color = PurpleColab,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun BottomNavColab() {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.List, contentDescription = "Tareas") },
            label = { Text("Tareas") },
            selected = true,
            onClick = {}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Gestionar") },
            label = { Text("Gestionar") },
            selected = false,
            onClick = {}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.DateRange, contentDescription = "Calendario") },
            label = { Text("Calendario") },
            selected = false,
            onClick = {}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            selected = false,
            onClick = {}
        )
    }
}