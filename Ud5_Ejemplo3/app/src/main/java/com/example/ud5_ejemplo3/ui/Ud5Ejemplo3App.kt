package com.example.ud5_ejemplo3.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ud5_ejemplo3.R
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

import androidx.compose.runtime.collectAsState

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ud5_ejemplo3.ui.pantallas.PantallaInicio
import com.example.ud5_ejemplo3.ui.pantallas.PrimeraPantalla
import com.example.ud5_ejemplo3.ui.pantallas.SegundaPantalla

enum class Pantallas(@StringRes val titulo: Int) {
    Inicio(titulo = R.string.pantalla_inicio),
    Pantalla1(titulo = R.string.pantalla1),
    Pantalla2(titulo = R.string.pantalla2)
}

@Composable
fun Ud5Ejemplo3App(
    viewModel: AppViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    // Para controlar que en la primera pantalla no aparezca la flecha de atrás en el TopBar
    // controlamos si en la pila de retroceso hay alguna pantalla destrás de la actual.
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
                onNavegarAtras = {navController.navigateUp()}
            )
        }
    ) { innerPadding ->

        val uiState by viewModel.appUIState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = Pantallas.Inicio.name,
            modifier = Modifier.padding(innerPadding)
        ){
            // Grafo de las rutas
            composable(route = Pantallas.Inicio.name) {
                PantallaInicio(
                    onBotonSiguientePulsado = {
                        viewModel.actualizarNumero1(it)
                        navController.navigate(Pantallas.Pantalla1.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            composable(route = Pantallas.Pantalla1.name) {
                PrimeraPantalla(
                    onBotonSiguientePulsado = {
                        viewModel.actualizarNumero2(it)
                        navController.navigate(Pantallas.Pantalla2.name)
                    },
                    onBotonAtrasPulsado = {navController.navigate(Pantallas.Inicio.name)},
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            composable(route = Pantallas.Pantalla2.name) {
                SegundaPantalla(
                    appUIState = uiState,
                    onBotonAtrasPulsado = {navController.navigate(Pantallas.Pantalla1.name)},
                    // Al ser la última pantalla y volver al inicio debemos vaciar la pila de retroceso y quedarnos solo con la pantalla inicial.
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