package com.example.ud5_ejemplo3.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ud5_ejemplo3.modelo.AppUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel: ViewModel() {
    private val _appUIState = MutableStateFlow(AppUIState())
    val appUIState: StateFlow<AppUIState> = _appUIState.asStateFlow()

    var respuestaNumero1 by mutableStateOf("")
        private set
    var respuestaNumero2 by mutableStateOf("")
        private set

    fun actualizarNumero1(num: String) {
        respuestaNumero1 = num

        _appUIState.update { estadoActual ->
            estadoActual.copy(
                numero1 = respuestaNumero1
            )
        }
    }



    fun actualizarNumero2(num: String) {
        respuestaNumero2 = num

        _appUIState.update { estadoActual ->
            estadoActual.copy(
                numero2 = respuestaNumero2
            )
        }
    }


}