package com.example.casetrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.DoneOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.casetrack.ui.components.TarjetaAccion
import com.example.casetrack.ui.theme.AmbarCaso
import com.example.casetrack.ui.theme.AzulPetroleo
import com.example.casetrack.ui.theme.FondoClaro
import com.example.casetrack.ui.theme.Turquesa
import com.example.casetrack.ui.theme.VerdeCaso

@Composable /*Esta función participa en la construcción de la interfaz gráfica.*/
fun InicioScreen(modifier: Modifier, buscaCasos: () -> Unit, registroCasos: () -> Unit, detalleCasos: () -> Unit, casosTerminados: () -> Unit ) {
    Column(modifier = modifier.padding(16.dp).background(FondoClaro), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        TarjetaAccion(
            icono = Icons.Default.Folder,
            titulo = "Mis casos",
            descripcion = "Consulta tus casos",
            color = AzulPetroleo,
            onClick = {
                buscaCasos()
            }
        )

        TarjetaAccion(
            icono = Icons.Default.Add,
            titulo = "Nuevo caso",
            descripcion = "Registra un nuevo caso",
            color = Turquesa,
            onClick = {
                registroCasos()
            }
        )

        TarjetaAccion(
            icono = Icons.Default.Search,
            titulo = "Estadisticas",
            descripcion = "Busca un caso registrado",
            color = AmbarCaso,
            onClick = {
                detalleCasos()
            }
        )

        TarjetaAccion(
            icono = Icons.Default.DoneOutline,
            titulo = "Casos cerrados",
            descripcion = "Consulta los casos finalizados",
            color = VerdeCaso,
            onClick = {
                casosTerminados()
            }
        )
    }
}