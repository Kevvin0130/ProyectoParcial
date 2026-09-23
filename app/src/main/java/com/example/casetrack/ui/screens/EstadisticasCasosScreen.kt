package com.example.casetrack.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.casetrack.Logic.CasoViewModel
import com.example.casetrack.ui.theme.AmbarCaso
import com.example.casetrack.ui.theme.AzulCaso
import com.example.casetrack.ui.theme.VerdeCaso

@Composable
fun EstadisticasCasosScreen(viewModel: CasoViewModel) {

    LaunchedEffect(Unit) {
        viewModel.obtenerCasos()
    }

    val casos by viewModel.casos.collectAsState()

    val abiertos = casos.count { it.estado == "ABIERTO" }
    val enInvestigacion = casos.count { it.estado == "EN_INVESTIGACION" }
    val cerrados = casos.count { it.estado == "CERRADO" }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Estadisticas", style = MaterialTheme.typography.headlineSmall)
        Text("Total de casos registrados: ${casos.size}", style = MaterialTheme.typography.bodyMedium)

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            TarjetaEstadistica("Abiertos", abiertos, AzulCaso, Modifier.fillMaxWidth().weight(1f))
            TarjetaEstadistica("En investigacion", enInvestigacion, AmbarCaso, Modifier.fillMaxWidth().weight(1f))
            TarjetaEstadistica("Cerrados", cerrados, VerdeCaso, Modifier.fillMaxWidth().weight(1f))
        }
    }
}

@Composable
private fun TarjetaEstadistica(titulo: String, valor: Int, color: Color, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(1.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "$valor", style = MaterialTheme.typography.headlineSmall, color = color)
            Text(text = titulo, style = MaterialTheme.typography.labelSmall)
        }
    }
}