package com.example.simulacro.ui.pantallas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.simulacro.modelo.Usuario
import com.example.simulacro.ui.UsuarioUIState

@Composable
fun PantallaEjercicio2(
    appUIState: UsuarioUIState,
    onUsuarioEliminado: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val lista = (appUIState as? UsuarioUIState.ObtenerTodosExito)?.listaUsuarios ?: emptyList()

    var usuarioAEliminar by remember { mutableStateOf<Usuario?>(null) }

    Column(modifier = modifier.padding(16.dp)) {

        Text(
            text = "Eliminar Usuarios",
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium
        )

        LazyColumn {
            items(lista) { usuario ->
                ListItem(
                    headlineContent = { Text(usuario.nombre.trim().ifEmpty { "(sin nombre)" }) },
                    supportingContent = { Text(usuario.telefono.trim().ifEmpty { "(sin teléfono)" }) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { usuarioAEliminar = usuario }
                )

                HorizontalDivider()
                // Tap para pedir confirmación
                Spacer(Modifier.height(2.dp))
            }
        }

    }

    // Dialogo confirmación al pulsar un usuario
    if (usuarioAEliminar != null) {
        val u = usuarioAEliminar!!
        AlertDialog(
            onDismissRequest = { usuarioAEliminar = null },
            title = { Text("Confirmación") },
            text = { Text("¿Seguro que quieres borrar a ${u.nombre.trim().ifEmpty { u.id }}?") },
            confirmButton = {
                TextButton(onClick = {
                    onUsuarioEliminado(u.id)
                    usuarioAEliminar = null
                }) { Text("Sí") }
            },
            dismissButton = {
                TextButton(onClick = { usuarioAEliminar = null }) { Text("No") }
            }
        )
    }
}
