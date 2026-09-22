package com.example.casetrack.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CasoDao {

    @Insert
    suspend fun insertarCaso(caso: CasoEntity)

    @Query("SELECT * FROM casos")
    suspend fun obtenerCasos(): List<CasoEntity>

    @Update
    suspend fun actualizarCaso(caso: CasoEntity)

    @Delete
    suspend fun eliminarCaso(caso: CasoEntity)
}
//Las funciones suspend permiten que esa operación se ejecute de forma apropiada sin bloquear el hilo principal.