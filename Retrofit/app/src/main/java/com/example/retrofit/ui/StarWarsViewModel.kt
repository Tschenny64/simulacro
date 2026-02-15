package com.example.retrofit.ui

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.retrofit.StarWarsAplicacion
import com.example.retrofit.datos.PersonajesRepositorio
import com.example.retrofit.modelo.Respuesta
import kotlinx.coroutines.launch
import okhttp3.internal.platform.android.DeferredSocketAdapter
import java.io.IOException

sealed interface StarwarsUIState {
    data class Exito(val respuesta: Respuesta) : StarwarsUIState
    object Error : StarwarsUIState
    object Cargando : StarwarsUIState
}
class StarWarsViewModel(private val personajesRepositorio: PersonajesRepositorio) : ViewModel() {
    var starwarsUIState: StarwarsUIState by mutableStateOf(StarwarsUIState.Cargando)
        private set

    init {
        obtenerPersonaje()
    }
    fun obtenerPersonaje() {
        viewModelScope.launch {
            starwarsUIState = StarwarsUIState.Cargando
            starwarsUIState = try {
                val listaPersonajes = personajesRepositorio.obtenerPersonajes()
                StarwarsUIState.Exito(listaPersonajes)
            } catch (e: IOException){
                StarwarsUIState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplicacion = (this[APPLICATION_KEY] as StarWarsAplicacion)
                val personajesRepositorio = aplicacion.contenedor.personajesRepositorio
                StarWarsViewModel(personajesRepositorio = personajesRepositorio)
            }
        }
    }
}