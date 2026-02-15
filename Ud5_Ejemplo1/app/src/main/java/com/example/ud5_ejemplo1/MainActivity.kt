package com.example.ud5_ejemplo1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ud5_ejemplo1.ui.pantallas.PantallaInicio
import com.example.ud5_ejemplo1.ui.pantallas.PrimeraPantalla
import com.example.ud5_ejemplo1.ui.pantallas.SegundaPantalla
import com.example.ud5_ejemplo1.ui.theme.Ud5_Ejemplo1Theme

enum class Pantallas {
    Inicio,
    Pantalla1,
    Pantalla2
     }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ud5_Ejemplo1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Ud5_Ejemplo1(
                        modifier = Modifier.padding(innerPadding)
                    )

                }
            }
        }
    }
}


@Composable
fun Ud5_Ejemplo1(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()) {

    NavHost(
        navController = navController,
        startDestination = Pantallas.Inicio.name,
        modifier = modifier
    ){
        // Grafo de las rutas
        composable(route = Pantallas.Inicio.name) {
            PantallaInicio(
                onBotonSiguientePulsado = { navController.navigate(Pantallas.Pantalla1.name) },
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        composable(route = Pantallas.Pantalla1.name) {
            PrimeraPantalla(
                onBotonSiguientePulsado = { navController.navigate(Pantallas.Pantalla2.name) },
                onBotonAtrasPulsado = { navController.navigate(Pantallas.Inicio.name) },
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        composable(route = Pantallas.Pantalla2.name) {
            SegundaPantalla(
                onBotonAtrasPulsado = { navController.navigate(Pantallas.Pantalla1.name) },
                onBotonInicioPulsado = { navController.navigate(Pantallas.Inicio.name) },
                modifier = Modifier
                    .fillMaxSize()
            )
        }

    }

}