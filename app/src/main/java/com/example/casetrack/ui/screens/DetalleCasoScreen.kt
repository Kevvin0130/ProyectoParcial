package com.example.casetrack.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.casetrack.Logic.CasoViewModel

@Composable
fun DetalleCasoScreen(viewModel: CasoViewModel) {

    val caso by viewModel.casoSeleccionado.collectAsState()

    var nuevoHallazgo by remember { mutableStateOf("") }
    var nuevaEvidencia by remember { mutableStateOf("") }

    if (caso == null) {
        Text("Selecciona un caso desde la lista para ver su detalle.", modifier = Modifier.padding(16.dp))
        return
    }

    val casoActual = caso!!

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = casoActual.titulo)
        Text(text = casoActual.descripcion)
        Text(text = "Fecha: ${casoActual.fecha}")
        Text(text = "Estado: ${casoActual.estado}")

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = { viewModel.cambiarEstado(casoActual, "ABIERTO") }) {
                Text("Abierto")
            }
            OutlinedButton(onClick = { viewModel.cambiarEstado(casoActual, "EN_INVESTIGACION") }) {
                Text("En investigación")
            }
            Button(onClick = { viewModel.cerrarCaso(casoActual) }) {
                Text("Cerrar caso")
            }
        }

        Divider()

        Text("Hallazgos")
        LazyColumn {
            items(casoActual.hallazgos) { hallazgo ->
                Text("• $hallazgo")
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
        }) {
            Text("Agregar hallazgo")
        }

        Divider()

        Text("Evidencias")
        LazyColumn {
            items(casoActual.evidencias) { evidencia ->
                Text("• $evidencia")
            }
        }
        TextField(
            value = nuevaEvidencia,
            onValueChange = { nuevaEvidencia = it },
            label = { Text("Nueva evidencia (nombre o descripción)") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            viewModel.agregarEvidencia(casoActual, nuevaEvidencia)
            nuevaEvidencia = ""
        }) {
            Text("Agregar evidencia")
        }
    }
}