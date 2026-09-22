package com.example.casetrack.Data
//Usamos el CasoRepository ya que este se convierte en el encargado de decidir de dónde obtener o dónde guardar los datos.

import com.example.casetrack.Data.CasoDao

class CasoRepository(
    private val casoDao: CasoDao
) {

    suspend fun insertarCaso(caso: CasoEntity) {
        casoDao.insertarCaso(caso)
    }

    suspend fun obtenerCasos(): List<CasoEntity> {
        return casoDao.obtenerCasos()
    }

    suspend fun actualizarCaso(caso: CasoEntity) {
        casoDao.actualizarCaso(caso)
    }

    suspend fun eliminarCaso(caso: CasoEntity) {
        casoDao.eliminarCaso(caso)
    }
}