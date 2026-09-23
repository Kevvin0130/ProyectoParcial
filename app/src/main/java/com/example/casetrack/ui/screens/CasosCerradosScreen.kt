package com.example.casetrack.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.casetrack.Data.CasoEntity
import com.example.casetrack.Logic.CasoViewModel
import com.example.casetrack.ui.components.EstadoBadge
import com.example.casetrack.ui.theme.TextoSecundario

@Composable
fun CasosCerradosScreen(
    viewModel: CasoViewModel,
    alSeleccionarCaso: (CasoEntity) -> Unit = {}
) {
    LaunchedEffect(Unit) {
        viewModel.obtenerCasos()
    }

    val casos by viewModel.casos.collectAsState()
    val casosCerrados = casos.filter { it.estado == "CERRADO" }

    var casoAEliminar by remember { mutableStateOf<CasoEntity?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Casos cerrados", style = MaterialTheme.typography.headlineSmall)

        if (casosCerrados.isEmpty()) {
            Text(
                "No hay casos cerrados.",
                color = TextoSecundario,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(casosCerrados) { caso ->
                Card(
                    onClick = { alSeleccionarCaso(caso) },
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(text = caso.titulo, style = MaterialTheme.typography.titleMedium)
                            Text(
                                text = caso.descripcion,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextoSecundario,
                                maxLines = 2
                            )
                            Text(
                                text = caso.fecha,
                                style = MaterialTheme.typography.labelSmall,
                                color = TextoSecundario
                            )
                            EstadoBadge(estado = caso.estado, modifier = Modifier.padding(top = 4.dp))
                        }
                        IconButton(onClick = { casoAEliminar = caso }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar caso",
                                tint = TextoSecundario
                            )
                        }
                    }
                }
            }
        }
    }

    val caso = casoAEliminar
    if (caso != null) {
        AlertDialog(
            onDismissRequest = { casoAEliminar = null },
            title = { Text("Eliminar caso") },
            text = { Text("¿Seguro que quieres eliminar \"${caso.titulo}\"? Esta accion no se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.eliminarCaso(caso)
                    casoAEliminar = null
                }) { Text("Eliminar") }
            },
            dismissButton = {
                TextButton(onClick = { casoAEliminar = null }) { Text("Cancelar") }
            }
        )
    }
}
