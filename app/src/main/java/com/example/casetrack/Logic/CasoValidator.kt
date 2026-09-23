package com.example.casetrack.Logic

object CasoValidator {

    fun tituloValido(titulo: String): Boolean {
        return titulo.isNotBlank()
    }

    fun descripcionValida(descripcion: String): Boolean {
        return descripcion.isNotBlank()
    }

    fun casoValido(titulo: String, descripcion: String): Boolean {
        return tituloValido(titulo) && descripcionValida(descripcion)
    }
}