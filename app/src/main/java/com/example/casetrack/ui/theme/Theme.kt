package com.example.casetrack.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Turquesa,
    secondary = AmbarCaso,
    tertiary = VerdeCaso,
    background = AzulOscuro,
    surface = AzulOscuro
)

private val LightColorScheme = lightColorScheme(
    primary = AzulPetroleo,
    secondary = Turquesa,
    tertiary = AmbarCaso,
    background = FondoClaro,
    surface = Blanco
)

@Composable
fun CasetrackTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}