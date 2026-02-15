package com.example.simulacro.ui.pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.simulacro.R
import com.example.simulacro.modelo.Mensaje
import com.example.simulacro.modelo.Usuario


@Composable
fun PantallaInsertar(
    onInsertarPulsado: (Usuario) -> Unit,
    modifier: Modifier = Modifier
) {
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    val telefonoOk = telefono.length == 9 && telefono.all { it.isDigit() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(Modifier.height(16.dp))
        TextField(
            value = nombre,
            label = { Text(text = "Nombre") },
            onValueChange = {nombre = it}
        )

        Spacer(Modifier.height(16.dp))
        TextField(
            value = telefono,
            label = { Text(text = "Telefono") },
            onValueChange = {telefono = it},
            isError = telefono.isNotEmpty() && !telefonoOk
        )
        if (telefono.isNotEmpty() && !telefonoOk) {
            Text(text = stringResource(R.string.error_telefono))
                }

        Spacer(Modifier.height(16.dp))
        TextField(
            value = mensaje,
            label = { Text(text = "Mensaje") },
            onValueChange = {mensaje = it}
        )

        Spacer(Modifier.height(16.dp))

        Button(
            enabled = telefonoOk,
            onClick = {
                val usuario = Usuario(nombre = nombre, telefono = telefono, mensajes = listOf(Mensaje(enviado = true, mensaje = mensaje)))
                onInsertarPulsado(usuario)
            }) {
            Text(text = "Insertar")
        }
    }
}