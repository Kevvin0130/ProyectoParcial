package com.example.casetrack.Model

import java.time.LocalDate

data class Caso(
    val titulo: String,
    val descripcion: String,
    val fecha: LocalDate,
    val estado: EstadoCaso,
    val hallazgos: List<String>,
    val evidencias: List<String>
)
enum class EstadoCaso {
    ABIERTO,
    EN_INVESTIGACION,
    CERRADO
} //"El estado del caso representa su ciclo de vida y contempla CERRADO como estado final. De esta manera se evita almacenar dos propiedades que podrían entrar en contradicción."