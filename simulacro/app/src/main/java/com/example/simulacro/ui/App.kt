package com.example.simulacro.ui

import android.graphics.drawable.Icon
import android.widget.MediaController
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.simulacro.R
import com.example.simulacro.modelo.Ruta
import com.example.simulacro.modelo.Usuario
import com.example.simulacro.modelo.UsuarioBD
import com.example.simulacro.ui.pantallas.PantallaChat
import com.example.simulacro.ui.pantallas.PantallaEjercicio2
import com.example.simulacro.ui.pantallas.PantallaEjercicio3
import com.example.simulacro.ui.pantallas.PantallaInicial
import com.example.simulacro.ui.pantallas.PantallaInsertar
import com.example.simulacro.ui.pantallas.PantallaInsertarEjercicio3
import kotlinx.coroutines.selects.select


enum class Pantallas(@StringRes val titulo: Int) {
    Inicio(titulo = R.string.pantalla_inicio),
    Insertar(titulo = R.string.pantalla_insertar),
    Actualizar(titulo = R.string.pantalla_actualizar),
    Ejercicio2(titulo = R.string.pantalla_ejercicio2),
    Ejercicio3(titulo = R.string.pantalla_ejercicio3),
    InsertarEjercicio3(titulo = R.string.pantalla_insertar_ejercicio3)
}

val listaRutas = listOf(
    Ruta(Pantallas.Inicio.titulo, Pantallas.Inicio.name, Icons.Filled.Home, Icons.Outlined.Home),
    Ruta(Pantallas.Ejercicio2.titulo, Pantallas.Ejercicio2.name, Icons.Filled.Place, Icons.Outlined.Place),
    Ruta(Pantallas.Ejercicio3.titulo, Pantallas.Ejercicio3.name, Icons.Filled.Email, Icons.Outlined.Email)
)


@Composable
fun App(
    viewModel: UsuarioViewModel = viewModel(factory = UsuarioViewModel.Factory),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val pantallaActual = Pantallas.valueOf(backStackEntry?.destination?.route ?: Pantallas.Inicio.name)

    var selectedItem by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            AppTopBar(
                pantallaActual = pantallaActual,
                puedeNavegarAtras = navController.previousBackStackEntry != null,
                onNavegarAtras = { navController.navigateUp() }
            )
        },
        bottomBar = {
            NavigationBar {
                listaRutas.forEachIndexed { index, ruta ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = if (selectedItem == index) ruta.iconoLleno else ruta.iconoVacio,
                                contentDescription = stringResource(ruta.titulo)
                            )
                        },
                        label = { Text(stringResource(ruta.titulo)) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(ruta.ruta) {
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            if (pantallaActual == Pantallas.Inicio) {
                FloatingActionButton(
                    onClick = { navController.navigate(Pantallas.Insertar.name) }
                ) {
                    Icon(Icons.Filled.Edit, contentDescription = stringResource(R.string.pantalla_insertar))
                }
            }
            if (pantallaActual == Pantallas.Ejercicio3) {
                FloatingActionButton(
                    onClick = {navController.navigate(Pantallas.InsertarEjercicio3.name) }
                ) {
                    Icon(Icons.Filled.Done, contentDescription = stringResource(R.string.pantalla_insertar_ejercicio3))
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        val uiState = viewModel.usuarioUIState

        NavHost(
            navController = navController,
            startDestination = Pantallas.Inicio.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Pantallas.Inicio.name) {
                PantallaInicial(
                    appUIState = uiState,
                    onUsuarioObtenidos = { viewModel.obtenerTodos() },
                    onUsuarioPulsadoBD = { viewModel.obtenerUsuarioBDPorId(id)},
                    onUsuarioObtenidosBD = { viewModel.obtenerTodos()},
                    onUsuarioPulsado = {
                        viewModel.actualizarUsuarioPulsado(it)
                        navController.navigate(Pantallas.Actualizar.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(Pantallas.Insertar.name) {
                PantallaInsertar(
                    onInsertarPulsado = {
                        viewModel.insertarUsuario(it)
                        navController.popBackStack(Pantallas.Inicio.name, inclusive = false)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(Pantallas.Actualizar.name) {
                PantallaChat(
                    usuario = viewModel.usuarioPulsado,
                    onEnviar = {
                        viewModel.actualizarUsuario(it.id, it)
                        viewModel.actualizarUsuarioPulsado(it)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable(Pantallas.Ejercicio2.name) {
                PantallaEjercicio2(
                    appUIState = uiState,
                    onUsuarioEliminado = { id ->
                        viewModel.eliminarUsuario(id)
                        viewModel.obtenerUsuarios() // refrescar
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(Pantallas.Ejercicio3.name) {
                val listaBD = (uiState as? UsuarioUIState.ObtenerTodosExito)?.listaUsuariosBD ?: emptyList()
                PantallaEjercicio3(
                    lista = listaBD,
                    onUsuarioPulsado = { id ->
                        viewModel.obtenerUsuarioBDPorId(id)
                    }

                )
            }
            composable(Pantallas.InsertarEjercicio3.name) {
                PantallaInsertarEjercicio3(
                    onInsertarPulsado = { id ->
                        viewModel.insertarUsuarioBD(id)
                        navController.popBackStack(Pantallas.Ejercicio3.name, inclusive = false)                    }

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
) {
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
