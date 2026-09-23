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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.casetrack.Logic.CasoViewModel
import com.example.casetrack.ui.components.colorParaEstado
import com.example.casetrack.ui.theme.TextoSecundario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleCasoScreen(
    viewModel: CasoViewModel,
    alVolver: () -> Unit = {},
    alEliminarCaso: () -> Unit = {}
) {

    val caso by viewModel.casoSeleccionado.collectAsState()

    var nuevoHallazgo by remember { mutableStateOf("") }
    var nuevaEvidencia by remember { mutableStateOf("") }
    var enEdicion by remember { mutableStateOf(false) }
    var tituloEditado by remember { mutableStateOf("") }
    var descripcionEditada by remember { mutableStateOf("") }
    var conclusionEditada by remember { mutableStateOf("") }
    var mostrarConfirmacionEliminar by remember { mutableStateOf(false) }

    LaunchedEffect(caso?.id) {
        tituloEditado = caso?.titulo ?: ""
        descripcionEditada = caso?.descripcion ?: ""
        conclusionEditada = caso?.conclusion ?: ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(caso?.titulo ?: "Detalle del caso") },
                navigationIcon = {
                    IconButton(onClick = alVolver) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingInterno ->

        if (caso == null) {
            Column(modifier = Modifier.padding(paddingInterno).padding(16.dp)) {
                Text("Selecciona un caso desde la lista para ver su detalle.")
            }
            return@Scaffold
        }

        val casoActual = caso!!

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingInterno)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Card(shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(1.dp)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    if (enEdicion) {
                        TextField(
                            value = tituloEditado,
                            onValueChange = { tituloEditado = it },
                            label = { Text("Titulo") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        TextField(
                            value = descripcionEditada,
                            onValueChange = { descripcionEditada = it },
                            label = { Text("Descripcion") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = {
                                viewModel.editarCaso(casoActual, tituloEditado, descripcionEditada, casoActual.fecha)
                                enEdicion = false
                            }) { Text("Guardar cambios") }
                            OutlinedButton(onClick = { enEdicion = false }) { Text("Cancelar") }
                        }
                    } else {
                        Text(text = casoActual.descripcion, style = MaterialTheme.typography.bodyLarge)
                        Text(
                            text = "Fecha: ${casoActual.fecha}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextoSecundario
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(onClick = { enEdicion = true }) { Text("Editar") }
                            Button(onClick = { mostrarConfirmacionEliminar = true }) { Text("Eliminar caso") }
                        }
                    }
                }
            }

            Card(shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(1.dp)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Estado del caso", style = MaterialTheme.typography.titleMedium)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("ABIERTO" to "Abierto", "EN_INVESTIGACION" to "En investigacion", "CERRADO" to "Cerrado")
                            .forEach { (valor, etiqueta) ->
                                FilterChip(
                                    selected = casoActual.estado == valor,
                                    onClick = { viewModel.cambiarEstado(casoActual, valor) },
                                    label = { Text(etiqueta) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = colorParaEstado(valor)
                                    )
                                )
                            }
                    }
                }
            }

            Card(shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(1.dp)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Hallazgos", style = MaterialTheme.typography.titleMedium)
                    if (casoActual.hallazgos.isEmpty()) {
                        Text("Aun no hay hallazgos registrados.", color = TextoSecundario, style = MaterialTheme.typography.bodyMedium)
                    }
                    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
                        items(casoActual.hallazgos) { hallazgo ->
                            Text("• $hallazgo", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                    TextField(
                        value = nuevoHallazgo,
                        onValueChange = { nuevoHallazgo = it },
                        label = { Text("Nuevo hallazgo") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(onClick = {
                        viewModel.agregarHallazgo(casoActual, nuevoHallazgo)
                        nuevoHallazgo = ""
                    }) { Text("Agregar hallazgo") }
                }
            }

            Card(shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(1.dp)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Evidencias", style = MaterialTheme.typography.titleMedium)
                    if (casoActual.evidencias.isEmpty()) {
                        Text("Aun no hay evidencias registradas.", color = TextoSecundario, style = MaterialTheme.typography.bodyMedium)
                    }
                    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
                        items(casoActual.evidencias) { evidencia ->
                            Text("• $evidencia", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                    TextField(
                        value = nuevaEvidencia,
                        onValueChange = { nuevaEvidencia = it },
                        label = { Text("Nueva evidencia (nombre o descripcion)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(onClick = {
                        viewModel.agregarEvidencia(casoActual, nuevaEvidencia)
                        nuevaEvidencia = ""
                    }) { Text("Agregar evidencia") }
                }
            }

            Card(shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(1.dp)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Conclusion", style = MaterialTheme.typography.titleMedium)
                    TextField(
                        value = conclusionEditada,
                        onValueChange = { conclusionEditada = it },
                        label = { Text("Conclusion del caso") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(onClick = { viewModel.actualizarConclusion(casoActual, conclusionEditada) }) {
                        Text("Guardar conclusion")
                    }
                }
            }
        }

        if (mostrarConfirmacionEliminar) {
            AlertDialog(
                onDismissRequest = { mostrarConfirmacionEliminar = false },
                title = { Text("Eliminar caso") },
                text = { Text("¿Seguro que quieres eliminar \"${casoActual.titulo}\"? Esta accion no se puede deshacer.") },
                confirmButton = {
                    TextButton(onClick = {
                        mostrarConfirmacionEliminar = false
                        viewModel.eliminarCaso(casoActual, alTerminar = alEliminarCaso)
                    }) { Text("Eliminar") }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarConfirmacionEliminar = false }) { Text("Cancelar") }
                }
            )
        }
    }
}