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
import com.example.simulacro.modelo.Usuario
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException


sealed interface UsuarioUIState {

    data class ObtenerExito(val usuarios: List<Usuario>) : UsuarioUIState

    data class CrearExito(val usuario: Usuario) : UsuarioUIState

    data class ActualizarExito(val usuario: Usuario) : UsuarioUIState

    object Error : UsuarioUIState

    object Cargando : UsuarioUIState
}

class UsuarioViewModel(private val UsuarioRepositorio: UsuarioRepositorio) : ViewModel() {

    var usuarioUIState: UsuarioUIState by mutableStateOf(UsuarioUIState.Cargando)
        private set

    var usuarioPulsado: Usuario by mutableStateOf(Usuario("","","", mensajes = emptyList()))
        private set

    fun actualizarUsuarioPulsado(usuario: Usuario) {
        usuarioPulsado = usuario
    }

    init {
        obtenerUsuarios()
    }

    fun obtenerUsuarios() {
        viewModelScope.launch {
            usuarioUIState = UsuarioUIState.Cargando
            usuarioUIState = try {
                val listaUsuarios = UsuarioRepositorio.obtenerUsuarios()
                UsuarioUIState.ObtenerExito(listaUsuarios)
            } catch (e: IOException) {
                UsuarioUIState.Error
            } catch (e: HttpException) {
                UsuarioUIState.Error
            }
        }
    }

    fun insertarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            usuarioUIState = UsuarioUIState.Cargando
            usuarioUIState = try {
                val usuarioInsertado = UsuarioRepositorio.insertarUsuario(usuario)
                UsuarioUIState.CrearExito(usuarioInsertado)
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
                val usuarioActualizado = UsuarioRepositorio.actualizarUsuario(id, usuario)
                UsuarioUIState.ActualizarExito(usuarioActualizado)
            } catch (e: IOException) {
                UsuarioUIState.Error
            } catch (e: HttpException) {
                UsuarioUIState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplicacion = (this[APPLICATION_KEY] as Aplicacion)
                val UsuarioRepositorio = aplicacion.contenedor.UsuarioRepositorio
                UsuarioViewModel(UsuarioRepositorio = UsuarioRepositorio)
            }
        }
    }
}