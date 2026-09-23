package com.example.casetrack.Data

class FakeCasoDao : CasoDao {

    private val casos = mutableListOf<CasoEntity>()
    private var siguienteId = 1

    override suspend fun insertarCaso(caso: CasoEntity) {
        casos.add(caso.copy(id = siguienteId++))
    }

    override suspend fun obtenerCasos(): List<CasoEntity> {
        return casos.toList()
    }

    override suspend fun actualizarCaso(caso: CasoEntity) {
        val indice = casos.indexOfFirst { it.id == caso.id }
        if (indice != -1) {
            casos[indice] = caso
        }
    }

    override suspend fun eliminarCaso(caso: CasoEntity) {
        casos.removeAll { it.id == caso.id }
    }
}