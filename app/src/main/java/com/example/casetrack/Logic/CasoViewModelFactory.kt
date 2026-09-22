package com.example.casetrack.Logic
//Este factory lo que hace es construir ese viewmodel apartir del repository que se le mando
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.casetrack.Data.CasoRepository

class CasoViewModelFactory(
    private val repository: CasoRepository
) : ViewModelProvider.Factory { //Aca Cuando creemos la Factory le entregaremos: CasoRepository, Entonces la Factory lo guarda

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(CasoViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return CasoViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}