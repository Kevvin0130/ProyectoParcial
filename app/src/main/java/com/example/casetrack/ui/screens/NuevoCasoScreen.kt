package com.example.casetrack.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.casetrack.Data.CasoEntity
import com.example.casetrack.Logic.CasoValidator
import com.example.casetrack.Logic.CasoViewModel
import com.example.casetrack.ui.theme.RojoCaso
import com.example.casetrack.ui.theme.VerdeCaso
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NuevoCasoScreen(viewModel: CasoViewModel, modifier: Modifier = Modifier) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var guardadoExitoso by remember { mutableStateOf(false) }
    var mostrarError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Nuevo caso", style = MaterialTheme.typography.headlineSmall)
        Text("Registra la informacion basica de la investigacion", style = MaterialTheme.typography.bodyMedium)

        Card(shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(1.dp)) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Titulo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripcion") },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )

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
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Guardar caso") }

                if (guardadoExitoso) {
                    Text("Caso creado correctamente", color = VerdeCaso)
                }
                if (mostrarError) {
                    Text("El titulo y la descripcion no pueden estar vacios", color = RojoCaso)
                }
            }
        }
    }
}