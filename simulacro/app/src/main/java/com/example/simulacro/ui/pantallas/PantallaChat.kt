package com.example.simulacro.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.room.util.TableInfo
import com.example.simulacro.modelo.Mensaje
import com.example.simulacro.modelo.Usuario

@Composable
fun PantallaChat(
    usuario: Usuario,
    onEnviar: (Usuario) -> Unit,
    modifier: Modifier = Modifier
) {
    var texto by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(usuario.mensajes) { msg ->
                BurbujaMensaje(msg)
            }
        }
        HorizontalDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = texto,
                onValueChange = { texto = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Escribe un mensaje") },
                singleLine = true
            )

            Spacer(Modifier.width(8.dp))

            Button(
                enabled = texto.isNotBlank(),
                onClick = {
                    val nuevo = Mensaje(enviado = false, mensaje = texto.trim())
                    val actualizado = usuario.copy(mensajes = usuario.mensajes + nuevo)
                    onEnviar(actualizado)
                    texto = ""
                }
            ) {
                Text("Enviar")
            }
        }
    }
}

@Composable
private fun BurbujaMensaje(msg: Mensaje) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (msg.enviado) Arrangement.Start else Arrangement.End
    ) {
        Surface(
            tonalElevation = 2.dp,
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = msg.mensaje,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}