package com.example.casetrack

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.example.casetrack.Data.CasoDatabase
import com.example.casetrack.Data.CasoRepository
import com.example.casetrack.Logic.CasoViewModelFactory
import com.example.casetrack.ui.Navigation.NavGraph
import com.example.casetrack.ui.theme.CasetrackTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val database = CasoDatabase.obtenerDatabase(applicationContext)
        val repository = CasoRepository(database.casoDao())
        val factory = CasoViewModelFactory(repository)

        setContent {
            CasetrackTheme {
                NavGraph(factory = factory)
            }
        }
    }
}