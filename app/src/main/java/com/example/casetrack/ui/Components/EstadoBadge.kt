package com.example.casetrack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.casetrack.ui.theme.AmbarCaso
import com.example.casetrack.ui.theme.AzulCaso
import com.example.casetrack.ui.theme.RojoCaso
import com.example.casetrack.ui.theme.VerdeCaso

fun colorParaEstado(estado: String): Color = when (estado) {
    "ABIERTO" -> AzulCaso
    "EN_INVESTIGACION" -> AmbarCaso
    "CERRADO" -> VerdeCaso
    else -> RojoCaso
}

fun textoParaEstado(estado: String): String = when (estado) {
    "ABIERTO" -> "Abierto"
    "EN_INVESTIGACION" -> "En investigacion"
    "CERRADO" -> "Cerrado"
    else -> estado
}

@Composable
fun EstadoBadge(estado: String, modifier: Modifier = Modifier) {
    Text(
        text = textoParaEstado(estado),
        color = Color.White,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        modifier = modifier
            .background(color = colorParaEstado(estado), shape = RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}