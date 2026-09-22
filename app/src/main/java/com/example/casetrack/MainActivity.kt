package com.example.casetrack

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.casetrack.ui.Navigation.NavGraph
import com.example.casetrack.ui.theme.CasetrackTheme
import com.example.casetrack.ui.screens.InicioScreen
import com.example.casetrack.Data.CasoDatabase
import com.example.casetrack.Data.CasoRepository
import com.example.casetrack.Logic.CasoViewModelFactory

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val database = CasoDatabase.obtenerDatabase(applicationContext) //Aca le pedimos la base de datos de CaseTrack.Ya tenemos ese metodo dentro de CasoDatabase:

        val repository = CasoRepository(
            database.casoDao()
        ) //Entrega el DAO de casos. Y luego: CasoRepository(...) Construye un Repository utilizando ese DAO

        val factory = CasoViewModelFactory(repository) //Le damos el Repository a nuestra Factory. La Factory ya sabe cómo construir el ViewModel.
        setContent {
            CasetrackTheme {
                NavGraph(factory = factory)//Aqui le decimos al NavGraph, aquí tienes la Factory que acabé de crear
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CasetrackTheme {
        Greeting("Android")
    }
}