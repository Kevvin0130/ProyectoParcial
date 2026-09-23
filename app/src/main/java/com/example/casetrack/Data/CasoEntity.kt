package com.example.casetrack.Data

import androidx.room.Entity
import androidx.room.PrimaryKey
//Porque Entity? Porque Caso es nuestro modelo de la aplicación, mientras que CasoEntity será la representación de una fila de la tabla casos en SQLite.
//Caso Entity modelo de pertinencia, caso model modelo de la aplicacion
@Entity(tableName = "casos")//Llamamos a la tabla casos
data class CasoEntity( //Se usa un data class ya que está pensado justamente para objetos cuyo propósito principal es almacenar información.

    @PrimaryKey(autoGenerate = true)//La primary key la va a generar room automaticamente, en este caso sera el Id
    val id: Int = 0,

    val titulo: String,

    val descripcion: String,

    val fecha: String,

    val estado: String,

    //Regla minima: "El sistema debe permitir registrar entrevistas y sus principales hallazgos."
    val hallazgos: List<String> = emptyList(),

    //Regla minima: "Registrar hallazgos y evidencias"
    val evidencias: List<String> = emptyList(),

    // Regla minima: "Cada caso debe permitir registrar una conclusion."
    val conclusion: String = ""
)