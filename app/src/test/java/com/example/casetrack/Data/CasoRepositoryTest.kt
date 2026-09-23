package com.example.casetrack.Data

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class CasoRepositoryTest {

    private fun crearRepositorioVacio(): CasoRepository {
        return CasoRepository(FakeCasoDao())
    }

    @Test
    fun `insertar un caso permite luego encontrarlo en obtenerCasos`() = runBlocking {
        val repository = crearRepositorioVacio()
        repository.insertarCaso(
            CasoEntity(titulo = "Caso 1", descripcion = "Desc", fecha = "2026-01-01", estado = "ABIERTO")
        )

        val casos = repository.obtenerCasos()
        assertEquals(1, casos.size)
        assertEquals("Caso 1", casos.first().titulo)
    }

    @Test
    fun `actualizar un caso cambia su estado y conclusion`() = runBlocking {
        val repository = crearRepositorioVacio()
        repository.insertarCaso(
            CasoEntity(titulo = "Caso 2", descripcion = "Desc", fecha = "2026-01-01", estado = "ABIERTO")
        )
        val casoGuardado = repository.obtenerCasos().first()

        repository.actualizarCaso(casoGuardado.copy(estado = "CERRADO", conclusion = "Se cerro el caso"))

        val casoActualizado = repository.obtenerCasos().first()
        assertEquals("CERRADO", casoActualizado.estado)
        assertEquals("Se cerro el caso", casoActualizado.conclusion)
    }

    @Test
    fun `eliminar un caso lo remueve de la lista`() = runBlocking {
        val repository = crearRepositorioVacio()
        repository.insertarCaso(
            CasoEntity(titulo = "Caso 3", descripcion = "Desc", fecha = "2026-01-01", estado = "ABIERTO")
        )
        val casoGuardado = repository.obtenerCasos().first()

        repository.eliminarCaso(casoGuardado)

        assertTrue(repository.obtenerCasos().isEmpty())
    }
}
