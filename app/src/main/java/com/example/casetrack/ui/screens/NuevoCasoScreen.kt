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
import com.example.casetrack.Logic.CasoViewModel
import com.example.casetrack.Model.Caso
import com.example.casetrack.Model.EstadoCaso
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NuevoCasoScreen(viewModel: CasoViewModel, modifier: Modifier = Modifier) {
    var casoGuardado by remember {
        mutableStateOf<Caso?>(null)
    } //El Caso? significa que puede ser null. Al principio:casoGuardado = null, porque todavía no hemos guardado ningún caso. Después de pulsar Guardar:casoGuardado = nuevoCaso
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

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
                }//Aca en este onValueChange lo que hacemos es ir actualizando el text field y mostrar lo que el usuario va escribiendo
            )
        }
        Button(
            onClick = {
                viewModel.guardarCaso( //Estamos creando un objeto CasoEntity para que despues se lo pase al ViewModel
                    CasoEntity(
                        titulo = titulo,
                        descripcion = descripcion,
                        fecha = LocalDate.now().toString(),
                        estado = "ABIERTO"
                    )
                )
            }
        ) {
            Text("Guardar")
        }
        if (casoGuardado != null) {
            Text("Caso creado correctamente")
        }
    }
}