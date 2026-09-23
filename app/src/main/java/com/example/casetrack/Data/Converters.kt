package com.example.casetrack.Data

import androidx.room.TypeConverter

// Room solo sabe guardar tipos simples (String, Int, etc.), por eso para guardar
// listas (hallazgos, evidencias) necesitamos "traducirlas" a un solo String y viceversa.
// Usamos "|||" como separador porque es muy poco probable que aparezca dentro de un texto normal.
class Converters {

    @TypeConverter
    fun fromList(lista: List<String>): String {
        return lista.joinToString(separator = "|||")
    }

    @TypeConverter
    fun toList(data: String): List<String> {
        if (data.isEmpty()) return emptyList()
        return data.split("|||")
    }
}