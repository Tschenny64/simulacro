package com.example.crud.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.crud.Aplicacion
import com.example.crud.datos.Repositorio
import com.example.crud.modelo.Trabajador
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed interface TrabajadorUIState {
    data class ObtenerExito(val trabajadores: List<Trabajador>) : TrabajadorUIState
    data class CrearExito(val trabajador: Trabajador) : TrabajadorUIState
    data class ActualizarExito(val trabajador: Trabajador) : TrabajadorUIState
    data class EliminarExito(val id: String) : TrabajadorUIState

    object Error : TrabajadorUIState
    object Cargando : TrabajadorUIState
}

class ViewModel(private val Repositorio: Repositorio) : ViewModel() {
    var trabajadorUIState: TrabajadorUIState by mutableStateOf(TrabajadorUIState.Cargando)
        private set

    var trabajadorPulsado: Trabajador by mutableStateOf(Trabajador("","","",""))
        private set

    fun actualizarTrabajadorPulsado(trabajador: Trabajador) {
        trabajadorPulsado = trabajador
    }

    init {
        obtenerTrabajadores()
    }

    fun obtenerTrabajadores() {
        viewModelScope.launch {
            trabajadorUIState = TrabajadorUIState.Cargando
            trabajadorUIState = try {
                val listaTrabajadores = Repositorio.obtenerTrabajadores()
                TrabajadorUIState.ObtenerExito(listaTrabajadores)
            } catch (e: IOException) {
                TrabajadorUIState.Error
            } catch (e: HttpException) {
                TrabajadorUIState.Error
            }
        }
    }

    fun insertarTrabajador(trabajador: Trabajador) {
        viewModelScope.launch {
            trabajadorUIState = TrabajadorUIState.Cargando
            trabajadorUIState = try {
                val trabajadorInsertado = Repositorio.insertarTrabajador(trabajador)
                TrabajadorUIState.CrearExito(trabajadorInsertado)
            } catch (e: IOException) {
                TrabajadorUIState.Error
            } catch (e: HttpException) {
                TrabajadorUIState.Error
            }
        }
    }

    fun actualizarTrabajador(id: String, trabajador: Trabajador) {
        viewModelScope.launch {
            trabajadorUIState = TrabajadorUIState.Cargando
            trabajadorUIState = try {
                val trabajadorActualizado = Repositorio.actualizarTrabajador(id, trabajador)
                TrabajadorUIState.CrearExito(trabajadorActualizado)
            } catch (e: IOException) {
                TrabajadorUIState.Error
            } catch (e: HttpException) {
                TrabajadorUIState.Error
            }
        }
    }

    fun eliminarTrabajador(id: String) {
        viewModelScope.launch {
            trabajadorUIState = TrabajadorUIState.Cargando
            trabajadorUIState = try {
                val trabajadorEliminado = Repositorio.eliminarTrabajador(id)
                TrabajadorUIState.EliminarExito(id)
            } catch (e: IOException) {
                TrabajadorUIState.Error
            } catch (e: HttpException) {
                TrabajadorUIState.Error
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplicacion = (this[APPLICATION_KEY] as Aplicacion)
                val Repositorio = aplicacion.contenedor.Repositorio
                ViewModel(Repositorio = Repositorio)
            }
        }
    }
}