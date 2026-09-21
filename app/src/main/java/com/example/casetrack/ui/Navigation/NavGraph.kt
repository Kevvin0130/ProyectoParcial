package com.example.casetrack.ui.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.casetrack.Model.Caso
import com.example.casetrack.ui.screens.CasosCerradosScreen
import com.example.casetrack.ui.screens.CasosScreen
import com.example.casetrack.ui.screens.EstadisticasCasosScreen
import com.example.casetrack.ui.screens.InicioScreen
import com.example.casetrack.ui.screens.NuevoCasoScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph() {
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
            CasosScreen()
        }
        composable("registro"){
            NuevoCasoScreen()
        }
        composable("detalles"){
            EstadisticasCasosScreen()
        }
        composable("finalizado") {
            CasosCerradosScreen()
        }
    }
}