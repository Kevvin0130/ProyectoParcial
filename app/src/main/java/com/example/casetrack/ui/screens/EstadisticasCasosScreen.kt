package com.example.casetrack.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.casetrack.Logic.CasoViewModel

//Cumple el requisito del enunciado: "visualizar un resumen general de los casos"
@Composable
fun EstadisticasCasosScreen(viewModel: CasoViewModel) {

    LaunchedEffect(Unit) { //Al entrar a esta pantalla, pedimos los casos actualizados
        viewModel.obtenerCasos()
    }

    val casos by viewModel.casos.collectAsState()

    val abiertos = casos.count { it.estado == "ABIERTO" }
    val enInvestigacion = casos.count { it.estado == "EN_INVESTIGACION" }
    val cerrados = casos.count { it.estado == "CERRADO" }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Estadísticas de tus casos")
        Text("Total de casos: ${casos.size}")
        Text("Abiertos: $abiertos")
        Text("En investigación: $enInvestigacion")
        Text("Cerrados: $cerrados")
    }
}