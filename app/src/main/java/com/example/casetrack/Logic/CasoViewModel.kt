package com.example.casetrack.Logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.casetrack.Data.CasoEntity
import com.example.casetrack.Data.CasoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CasoViewModel(
    private val repository: CasoRepository
) : ViewModel() {

    private val _casos = MutableStateFlow<List<CasoEntity>>(emptyList())
    val casos: StateFlow<List<CasoEntity>> = _casos

    // ---- Busqueda ----
    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda

    fun actualizarBusqueda(texto: String) {
        _busqueda.value = texto
    }

    // Se recalcula sola cada vez que cambian los casos o el texto buscado.
    val casosFiltrados: StateFlow<List<CasoEntity>> =
        combine(_casos, _busqueda) { listaCasos, textoBusqueda ->
            if (textoBusqueda.isBlank()) {
                listaCasos
            } else {
                listaCasos.filter { caso ->
                    caso.titulo.contains(textoBusqueda, ignoreCase = true) ||
                            caso.descripcion.contains(textoBusqueda, ignoreCase = true)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _casoSeleccionado = MutableStateFlow<CasoEntity?>(null)
    val casoSeleccionado: StateFlow<CasoEntity?> = _casoSeleccionado

    fun seleccionarCaso(caso: CasoEntity) {
        _casoSeleccionado.value = caso
    }

    fun guardarCaso(caso: CasoEntity) {
        viewModelScope.launch {
            repository.insertarCaso(caso)
            obtenerCasos()
        }
    }

    fun obtenerCasos() {
        viewModelScope.launch {
            _casos.value = repository.obtenerCasos()
        }
    }

    // ---- Editar caso (titulo, descripcion, fecha) ----
    fun editarCaso(caso: CasoEntity, nuevoTitulo: String, nuevaDescripcion: String, nuevaFecha: String) {
        if (!CasoValidator.casoValido(nuevoTitulo, nuevaDescripcion)) return
        val casoActualizado = caso.copy(
            titulo = nuevoTitulo,
            descripcion = nuevaDescripcion,
            fecha = nuevaFecha
        )
        viewModelScope.launch {
            repository.actualizarCaso(casoActualizado)
            _casoSeleccionado.value = casoActualizado
            obtenerCasos()
        }
    }

    // ---- Eliminar caso ----
    fun eliminarCaso(caso: CasoEntity, alTerminar: () -> Unit = {}) {
        viewModelScope.launch {
            repository.eliminarCaso(caso)
            _casoSeleccionado.value = null
            obtenerCasos()
            alTerminar()
        }
    }

    // ---- Hallazgos y evidencias ----
    fun agregarHallazgo(caso: CasoEntity, hallazgo: String) {
        if (hallazgo.isBlank()) return
        val casoActualizado = caso.copy(hallazgos = caso.hallazgos + hallazgo)
        viewModelScope.launch {
            repository.actualizarCaso(casoActualizado)
            _casoSeleccionado.value = casoActualizado
            obtenerCasos()
        }
    }

    fun agregarEvidencia(caso: CasoEntity, evidencia: String) {
        if (evidencia.isBlank()) return
        val casoActualizado = caso.copy(evidencias = caso.evidencias + evidencia)
        viewModelScope.launch {
            repository.actualizarCaso(casoActualizado)
            _casoSeleccionado.value = casoActualizado
            obtenerCasos()
        }
    }

    // ---- Conclusion ----
    fun actualizarConclusion(caso: CasoEntity, conclusion: String) {
        val casoActualizado = caso.copy(conclusion = conclusion)
        viewModelScope.launch {
            repository.actualizarCaso(casoActualizado)
            _casoSeleccionado.value = casoActualizado
            obtenerCasos()
        }
    }

    // ---- Estado y cierre del caso ----
    fun cambiarEstado(caso: CasoEntity, nuevoEstado: String) {
        val casoActualizado = caso.copy(estado = nuevoEstado)
        viewModelScope.launch {
            repository.actualizarCaso(casoActualizado)
            _casoSeleccionado.value = casoActualizado
            obtenerCasos()
        }
    }

    fun cerrarCaso(caso: CasoEntity) {
        cambiarEstado(caso, "CERRADO")
    }
}