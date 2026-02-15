package com.example.estudiar

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
enum class Pantallas(@StringRes val titulo: Int) {
    Inicio(titulo = R.string.pantalla_inicio),
    Pantalla1(titulo = R.string.pantalla1),
    Pantalla2(titulo = R.string.pantalla2),
    Pantalla3(titulo = R.string.pantalla3)
}

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController()
) {
    // Para controlar que en la primera pantalla no aparezca la flecha de atrás en el TopBar
    // controlamos si en la pila de retroceso hay alguna patnalla destrás de la actual.
    // Si la hay, mostramos la flecha.
    val pilaRetroceso by navController.currentBackStackEntryAsState()

    val pantallaActual = Pantallas.valueOf(
        pilaRetroceso?.destination?.route ?: Pantallas.Inicio.name
    )

    Scaffold(
        topBar = {
            AppTopBar(
                pantallaActual = pantallaActual,
                puedeNavegarAtras = navController.previousBackStackEntry != null,
                onNavegarAtras = { navController.navigateUp()}
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Pantallas.Inicio.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Pantallas.Inicio.name) {
                Inicio(
                    onBotonSiguientePulsado = {navController.navigate(Pantallas.Pantalla1.name)},
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            composable(route = Pantallas.Pantalla1.name) {
                Pantalla1(
                    onBotonSiguientePulsado = {navController.navigate(Pantallas.Pantalla2.name)},
                    onBotonAtrasPulsado = {navController.navigate(Pantallas.Inicio.name)},
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            composable(route = Pantallas.Pantalla2.name) {
                Pantalla2(
                    onBotonAtrasPulsado = {navController.navigate(Pantallas.Pantalla1.name)},
                    onBotonSiguientePulsado = { navController.navigate(Pantallas.Pantalla3.name)},
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            composable(route = Pantallas.Pantalla3.name) {
                Pantalla3(
                    onBotonAtrasPulsado = { navController.navigate(Pantallas.Pantalla2.name)},
                    onBotonInicioPulsado = {navController.popBackStack(Pantallas.Inicio.name, inclusive = false)},
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            }
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    pantallaActual: Pantallas,
    puedeNavegarAtras: Boolean,
    onNavegarAtras: () -> Unit,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = { Text(text = stringResource(id = pantallaActual.titulo)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {
            if(puedeNavegarAtras) {
                IconButton(onClick = onNavegarAtras) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.atras)
                    )
                }
            }
        },
        modifier = modifier
    )
}