package com.example.casetrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.DoneOutline
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.casetrack.ui.components.TarjetaAccion
import com.example.casetrack.ui.theme.AmbarCaso
import com.example.casetrack.ui.theme.AzulPetroleo
import com.example.casetrack.ui.theme.FondoClaro
import com.example.casetrack.ui.theme.TextoSecundario
import com.example.casetrack.ui.theme.Turquesa
import com.example.casetrack.ui.theme.VerdeCaso

@Composable
fun InicioScreen(
    modifier: Modifier,
    buscaCasos: () -> Unit,
    registroCasos: () -> Unit,
    detalleCasos: () -> Unit,
    casosTerminados: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FondoClaro)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column {
            Text("CaseTrack", style = MaterialTheme.typography.headlineSmall)
            Text(
                "Organiza tus investigaciones y casos",
                style = MaterialTheme.typography.bodyMedium,
                color = TextoSecundario
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            TarjetaAccion(
                icono = Icons.Default.Folder,
                titulo = "Mis casos",
                descripcion = "Consulta y busca tus casos",
                color = AzulPetroleo,
                onClick = buscaCasos
            )
            TarjetaAccion(
                icono = Icons.Default.Add,
                titulo = "Nuevo caso",
                descripcion = "Registra un nuevo caso",
                color = Turquesa,
                onClick = registroCasos
            )
            TarjetaAccion(
                icono = Icons.Default.BarChart,
                titulo = "Estadisticas",
                descripcion = "Resumen general de tus casos",
                color = AmbarCaso,
                onClick = detalleCasos
            )
            TarjetaAccion(
                icono = Icons.Default.DoneOutline,
                titulo = "Casos cerrados",
                descripcion = "Consulta los casos finalizados",
                color = VerdeCaso,
                onClick = casosTerminados
            )
        }
    }
}