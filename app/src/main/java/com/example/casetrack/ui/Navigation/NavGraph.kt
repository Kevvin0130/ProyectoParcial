package com.example.casetrack.ui.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.casetrack.Logic.CasoViewModel
import com.example.casetrack.Logic.CasoViewModelFactory
import com.example.casetrack.Model.Caso
import com.example.casetrack.ui.screens.CasosCerradosScreen
import com.example.casetrack.ui.screens.CasosScreen
import com.example.casetrack.ui.screens.EstadisticasCasosScreen
import com.example.casetrack.ui.screens.InicioScreen
import com.example.casetrack.ui.screens.NuevoCasoScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(factory: CasoViewModelFactory) { //Para ejecutar NavGraph, necesitamos un CasoViewModelFactory.Ese caso lo creamos en MainActivity
    val navController = rememberNavController() //crea nuestro controlador de navegación
    val casos = remember {
        mutableStateListOf<Caso>()
    }
    NavHost(navController = navController, startDestination = "inicio") { //crea el espacio donde se van a mostrar nuestras pantallas. startDestination = "inicio" significa: Cuando arranque la navegación, empieza en la ruta "inicio".
        composable("inicio") {
            InicioScreen(
                modifier = Modifier,
                buscaCasos = {
                    navController.navigate("buscar")//Este "Buscar" es el nombre de la ruta de navegacion para poder asociarla con la pantalla CasosScreen que esta en el composable.
                },
                registroCasos = {
                    navController.navigate("registro")
                },
                detalleCasos = {
                    navController.navigate("detalles")
                },
                casosTerminados = {
                    navController.navigate("finalizado")
                }
            )
        }

        composable("buscar") {

            val viewModel: CasoViewModel = viewModel(
                factory = factory
            )

            CasosScreen(
                viewModel = viewModel
            )
        }
        composable("registro") {

            val viewModel: CasoViewModel = viewModel(
                factory = factory
            )

            NuevoCasoScreen(
                viewModel = viewModel //Aca le pasamos el ViewModel a la pantalla
            )
        } //Aca dentro de registro necesitamos obtener un CasoViewModel. Pero nuestro ViewModel tiene este constructor: CasoViewModel(repository)
        // Entonces viewModel() necesita saber como construir el viewModel. Y ahí le damos: factory = factory. Es decir: Usa ESTA Factory para construirlo

        composable("detalles"){
            EstadisticasCasosScreen()
        }
        composable("finalizado") {
            CasosCerradosScreen()
        }
    }
}