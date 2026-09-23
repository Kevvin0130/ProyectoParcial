package com.example.casetrack.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.casetrack.Data.CasoEntity
import com.example.casetrack.Logic.CasoValidator
import com.example.casetrack.Logic.CasoViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NuevoCasoScreen(viewModel: CasoViewModel, modifier: Modifier = Modifier) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var guardadoExitoso by remember { mutableStateOf(false) }
    var mostrarError by remember { mutableStateOf(false) } //true si el usuario intento guardar con campos vacios

    Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Column(horizontalAlignment = Alignment.Start) {
            Text("Titulo")
            TextField(
                value = titulo,
                onValueChange = { nuevoTexto ->
                    titulo = nuevoTexto
                },
                label = {
                    Text("Título")
                }
            )
        }
        Column(horizontalAlignment = Alignment.Start) {
            Text("Desripcion")
            TextField(
                value = descripcion,
                onValueChange = { nuevoTexto ->
                    descripcion = nuevoTexto
                }
            )
        }
        Button(
            onClick = {
                if (CasoValidator.casoValido(titulo, descripcion)) {
                    viewModel.guardarCaso(
                        CasoEntity(
                            titulo = titulo,
                            descripcion = descripcion,
                            fecha = LocalDate.now().toString(),
                            estado = "ABIERTO"
                        )
                    )
                    titulo = ""
                    descripcion = ""
                    guardadoExitoso = true
                    mostrarError = false
                } else {
                    mostrarError = true
                    guardadoExitoso = false
                }
            }
        ) {
            Text("Guardar")
        }
        if (guardadoExitoso) {
            Text("Caso creado correctamente")
        }
        if (mostrarError) {
            Text("El título y la descripción no pueden estar vacíos")
        }
    }
}