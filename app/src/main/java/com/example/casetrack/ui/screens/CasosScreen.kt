package com.example.casetrack.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.casetrack.Logic.CasoViewModel

@Composable
fun CasosScreen(
    viewModel: CasoViewModel
) {

    LaunchedEffect(Unit) { //Esto significa que Cuando esta pantalla aparezca, se ejecute obtenerCasos()
        viewModel.obtenerCasos()
    }

    val casos by viewModel.casos.collectAsState() //casos contiene la lista que obtuvo de la base de datos. collectAsState() básicamente le dice a Compose:
    //Observa esta lista y vuelve a dibujar la pantalla cuando cambie.

    LazyColumn { //LazyColumn es una Column, pero pensada para listas.

        items(casos) { caso -> //Por cada elemento que haya dentro de casos

            Column {

                Text(text = caso.titulo)
                Text(text = caso.descripcion)
                Text(text = caso.fecha)
                Text(text = caso.estado)

            }
        }
    }
}