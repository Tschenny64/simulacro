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
import com.example.simulacro.R
import com.example.simulacro.modelo.Usuario
import com.example.simulacro.modelo.UsuarioBD


@Composable
fun PantallaInsertarEjercicio3(
    onInsertarPulsado: (UsuarioBD) -> Unit,
    modifier: Modifier = Modifier
) {
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(Modifier.height(16.dp))

        TextField(
            value = nombre,
            label = { Text(text = stringResource(R.string.nombre)) },
            onValueChange = {nombre = it}
        )

        Spacer(Modifier.height(16.dp))

        TextField(
            value = telefono,
            label = { Text(text = stringResource(R.string.telefono)) },
            onValueChange = {telefono = it}
        )
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                val usuario = UsuarioBD(nombre = nombre, telefono = telefono)
                    onInsertarPulsado(usuario)
            }
        ) {
            Text(text = "Insertar")
        }

    }
}