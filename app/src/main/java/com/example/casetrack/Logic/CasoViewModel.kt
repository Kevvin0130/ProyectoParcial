package com.example.casetrack.Logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.casetrack.Data.CasoEntity
import com.example.casetrack.Data.CasoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CasoViewModel(
    private val repository: CasoRepository //Aca se declara que para que exista un CasoViewModel, necesitamos recibir un CasoRepository
) : ViewModel() {
    private val _casos = MutableStateFlow<List<CasoEntity>>(emptyList())
    //StateFlow sirve para representar un estado que puede cambiar y que otras partes de la aplicación pueden observar. emptylist comienza con la lista vacia
    //List<CasoEntity> La información que vamos a guardar aquí será una lista de objetos CasoEntity
    val casos: StateFlow<List<CasoEntity>> = _casos
    //Aquí creamos otra referencia al mismo estado, pero lo mostramos como StateFlow no como MutableStateFlow
    //Por lo tanto, las pantallas pueden observarlo, pero no deberían modificarlo directamente.

    private val _casoSeleccionado = MutableStateFlow<CasoEntity?>(null)
    val casoSeleccionado: StateFlow<CasoEntity?> = _casoSeleccionado
    fun seleccionarCaso(caso: CasoEntity) {
        _casoSeleccionado.value = caso
    }
    fun guardarCaso(caso: CasoEntity) { //Esta funcion recibe un caso y se encarga de iniciar el proceso para guardarlo
        viewModelScope.launch {
            repository.insertarCaso(caso)
        } //Este launch ejecuta esta operación de guardado sin bloquear la interfaz, a esto se le conoce como una corrutina
    }
    fun obtenerCasos() {
        viewModelScope.launch { //launch significa que vamos a ejecutar una tarea que puede tardar sin bloquear la interfaz. Porque consultar una base de datos es una operación que no queremos hacer bloqueando la pantalla
            _casos.value = repository.obtenerCasos() //Esta parte es muy importante ya que Repository nos da los casos."
            // El Repository hace: casoDao.obtenerCasos() Y el DAO ejecuta: SELECT * FROM casos. Es decir recibimos un list
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