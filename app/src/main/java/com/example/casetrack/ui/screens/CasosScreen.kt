package com.example.casetrack.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.casetrack.Logic.CasoViewModel

@Composable
fun CasosScreen(
    viewModel: CasoViewModel
) {

    LaunchedEffect(Unit) {
        viewModel.obtenerCasos()
    }

    val casos by viewModel.casos.collectAsState()

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {

        Text("Mis casos") //Antes esta pantalla no tenia ningun titulo y se veia como si estuviera rota

        if (casos.isEmpty()) {
            Text("Todavía no has registrado ningún caso.")
        }

        LazyColumn {
            items(casos) { caso ->
                Column {
                    Text(text = caso.titulo)
                    Text(text = caso.descripcion)
                    Text(text = caso.fecha)
                    Text(text = caso.estado)
                }
                Divider()
            }
        }
    }
}