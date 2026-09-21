package com.example.casetrack.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import com.example.casetrack.ui.theme.Blanco
import com.example.casetrack.ui.theme.AzulPetroleo
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun TarjetaAccion(
    icono: ImageVector,
    titulo: String,
    descripcion: String,
    color: Color,
    onClick: () -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }// Creamos un objeto que recibe y comunica las interacciones

    val presionado by interactionSource.collectIsPressedAsState()
    // Observamos específicamente si la tarjeta está siendo presionada.
    // true  → el usuario la está presionando
    // false → el usuario no la está presionando
    val escala by animateFloatAsState(
        targetValue = if (presionado) 0.94f else 1f,
        animationSpec = tween(100)
    )
    // Calculamos una escala animada para la tarjeta.
    // Si está presionada → 0.94 = 97% de su tamaño.
    // Si no está presionada → 1f = 100% de su tamaño.
    //Tween controla la velocidad de la animación. 100
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {         // Aplicamos la escala calculada a la tarjeta.
                scaleX = escala
                scaleY = escala
            },
        interactionSource = interactionSource
    ) {

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = color,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icono,
                    contentDescription = null,
                    tint = Color.White
                ) //ImageVector es el dato que representa el dibujo, es decir es el icono, y usamos en esta linea Icon ya que es el componente de Compose que permite dibujar ese icono que le paso ImageVector en pantalla
            }
            Text(
                text = titulo
            )

            Text(
                text = descripcion
            )
        }
    }
}

