package com.example.casetrack.ui.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.casetrack.Logic.CasoViewModel
import com.example.casetrack.Logic.CasoViewModelFactory
import com.example.casetrack.ui.screens.CasosCerradosScreen
import com.example.casetrack.ui.screens.CasosScreen
import com.example.casetrack.ui.screens.DetalleCasoScreen
import com.example.casetrack.ui.screens.EstadisticasCasosScreen
import com.example.casetrack.ui.screens.InicioScreen
import com.example.casetrack.ui.screens.NuevoCasoScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(factory: CasoViewModelFactory) {
    val navController = rememberNavController()

    val viewModel: CasoViewModel = viewModel(factory = factory)

    NavHost(navController = navController, startDestination = "inicio") {
        composable("inicio") {
            InicioScreen(
                modifier = Modifier,
                buscaCasos = { navController.navigate("buscar") },
                registroCasos = { navController.navigate("registro") },
                detalleCasos = { navController.navigate("detalles") },
                casosTerminados = { navController.navigate("finalizado") }
            )
        }

        composable("buscar") {
            CasosScreen(
                viewModel = viewModel,
                alSeleccionarCaso = { caso ->
                    viewModel.seleccionarCaso(caso)
                    navController.navigate("detalleCaso")
                }
            )
        }
        composable("registro") {
            NuevoCasoScreen(viewModel = viewModel)
        }

        composable("detalles") {
            EstadisticasCasosScreen(viewModel = viewModel)
        }
        composable("finalizado") {
            CasosCerradosScreen(
                viewModel = viewModel,
                alSeleccionarCaso = { caso ->
                    viewModel.seleccionarCaso(caso)
                    navController.navigate("detalleCaso")
                }
            )
        }

        composable("detalleCaso") {
            DetalleCasoScreen(
                viewModel = viewModel,
                alVolver = { navController.popBackStack() },
                alEliminarCaso = { navController.popBackStack() }
            )
        }
    }
}