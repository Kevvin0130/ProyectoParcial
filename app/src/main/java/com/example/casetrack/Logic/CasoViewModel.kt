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
}