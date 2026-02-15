package com.example.simulacro.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.simulacro.Aplicacion
import com.example.simulacro.datos.UsuarioRepositorio
import com.example.simulacro.datos.UsuarioRepositorioBD
import com.example.simulacro.modelo.Usuario
import com.example.simulacro.modelo.UsuarioBD
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed interface UsuarioUIState {
    // Ambos (Retrofit + BD)
    data class ObtenerTodosExito(
        val listaUsuarios: List<Usuario>,
        val listaUsuariosBD: List<UsuarioBD>
    ) : UsuarioUIState

    // Solo BD
    data class ObtenerExitoBD(val usuarioBD: UsuarioBD) : UsuarioUIState

    // Solo Retrofit
    object CrearExito : UsuarioUIState
    object ActualizarExito : UsuarioUIState
    object EliminarExito : UsuarioUIState
    object Error : UsuarioUIState
    object Cargando : UsuarioUIState
}

class UsuarioViewModel(
    private val usuarioRepositorio: UsuarioRepositorio,
    private val usuarioRepositorioBD: UsuarioRepositorioBD
) : ViewModel() {

    var usuarioUIState: UsuarioUIState by mutableStateOf(UsuarioUIState.Cargando)
        private set

    var usuarioPulsado: Usuario by mutableStateOf(Usuario(id = "", nombre = "", telefono = "", mensajes = emptyList()))
        private set

    fun actualizarUsuarioPulsado(usuario: Usuario) {
        usuarioPulsado = usuario
    }

    init {
        obtenerTodos()
    }

    // ============== CARGAR TODO (RETROFIT + BD) ==============
    fun obtenerTodos() {
        viewModelScope.launch {
            usuarioUIState = UsuarioUIState.Cargando
            usuarioUIState = try {
                val listaUsuarios = usuarioRepositorio.obtenerUsuarios()
                val listaUsuariosBD = usuarioRepositorioBD.obtenerTodosBD()
                UsuarioUIState.ObtenerTodosExito(listaUsuarios, listaUsuariosBD)
            } catch (e: Exception) {
                UsuarioUIState.Error
            }
        }
    }

    // Si quieres seguir llamándolo obtenerUsuarios desde UI:
    fun obtenerUsuarios() = obtenerTodos()

    // ============== RETROFIT CRUD ==============
    fun insertarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            usuarioUIState = UsuarioUIState.Cargando
            usuarioUIState = try {
                val insertado = usuarioRepositorio.insertarUsuario(usuario)
                // refrescar lista para que la UI se actualice
                val listaUsuarios = usuarioRepositorio.obtenerUsuarios()
                val listaUsuariosBD = usuarioRepositorioBD.obtenerTodosBD()
                UsuarioUIState.ObtenerTodosExito(listaUsuarios, listaUsuariosBD)
            } catch (e: IOException) {
                UsuarioUIState.Error
            } catch (e: HttpException) {
                UsuarioUIState.Error
            }
        }
    }

    fun actualizarUsuario(id: String, usuario: Usuario) {
        viewModelScope.launch {
            usuarioUIState = UsuarioUIState.Cargando
            usuarioUIState = try {
                usuarioRepositorio.actualizarUsuario(id, usuario)
                // refrescar lista
                val listaUsuarios = usuarioRepositorio.obtenerUsuarios()
                val listaUsuariosBD = usuarioRepositorioBD.obtenerTodosBD()
                UsuarioUIState.ObtenerTodosExito(listaUsuarios, listaUsuariosBD)
            } catch (e: IOException) {
                UsuarioUIState.Error
            } catch (e: HttpException) {
                UsuarioUIState.Error
            }
        }
    }

    fun eliminarUsuario(id: String) {
        viewModelScope.launch {
            usuarioUIState = UsuarioUIState.Cargando
            usuarioUIState = try {
                usuarioRepositorio.eliminarUsuario(id)
                // refrescar lista para que desaparezca de la UI
                val listaUsuarios = usuarioRepositorio.obtenerUsuarios()
                val listaUsuariosBD = usuarioRepositorioBD.obtenerTodosBD()
                UsuarioUIState.ObtenerTodosExito(listaUsuarios, listaUsuariosBD)
            } catch (e: IOException) {
                UsuarioUIState.Error
            } catch (e: HttpException) {
                UsuarioUIState.Error
            }
        }
    }

    // ============== BD CRUD (si lo necesitas) ==============
    fun insertarUsuarioBD(usuarioBD: UsuarioBD) {
        viewModelScope.launch {
            usuarioUIState = UsuarioUIState.Cargando
            usuarioUIState = try {
                usuarioRepositorioBD.insertarBD(usuarioBD)
                // refrescar todo
                val listaUsuarios = usuarioRepositorio.obtenerUsuarios()
                val listaUsuariosBD = usuarioRepositorioBD.obtenerTodosBD()
                UsuarioUIState.ObtenerTodosExito(listaUsuarios, listaUsuariosBD)
            } catch (e: Exception) {
                UsuarioUIState.Error
            }
        }
    }

    fun actualizarUsuarioBD(usuarioBD: UsuarioBD) {
        viewModelScope.launch {
            usuarioUIState = UsuarioUIState.Cargando
            usuarioUIState = try {
                usuarioRepositorioBD.actualizarBD(usuarioBD)
                val listaUsuarios = usuarioRepositorio.obtenerUsuarios()
                val listaUsuariosBD = usuarioRepositorioBD.obtenerTodosBD()
                UsuarioUIState.ObtenerTodosExito(listaUsuarios, listaUsuariosBD)
            } catch (e: Exception) {
                UsuarioUIState.Error
            }
        }
    }

    fun eliminarUsuarioBD(id: Int) {
        viewModelScope.launch {
            usuarioUIState = UsuarioUIState.Cargando
            usuarioUIState = try {
                val usuarioBD = usuarioRepositorioBD.obtenerPorIdBD(id)
                usuarioRepositorioBD.eliminarBD(usuarioBD)

                val listaUsuarios = usuarioRepositorio.obtenerUsuarios()
                val listaUsuariosBD = usuarioRepositorioBD.obtenerTodosBD()
                UsuarioUIState.ObtenerTodosExito(listaUsuarios, listaUsuariosBD)
            } catch (e: Exception) {
                UsuarioUIState.Error
            }
        }
    }


    fun obtenerUsuarioBDPorId(id: Int) {
        viewModelScope.launch {
            usuarioUIState = UsuarioUIState.Cargando
            usuarioUIState = try {
                val usuarioBD = usuarioRepositorioBD.obtenerPorIdBD(id)
                UsuarioUIState.ObtenerExitoBD(usuarioBD)
            } catch (e: Exception) {
                UsuarioUIState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplicacion = (this[APPLICATION_KEY] as Aplicacion)
                val usuarioRepositorio = aplicacion.contenedor.UsuarioRepositorio
                val usuarioRepositorioBD = aplicacion.contenedor.usuarioRepositorioBD
                UsuarioViewModel(
                    usuarioRepositorio = usuarioRepositorio,
                    usuarioRepositorioBD = usuarioRepositorioBD
                )
            }
        }
    }
}
