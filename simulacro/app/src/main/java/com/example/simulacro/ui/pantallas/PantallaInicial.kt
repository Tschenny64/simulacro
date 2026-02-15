package com.example.simulacro.ui.pantallas

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.simulacro.R
import com.example.simulacro.modelo.Usuario
import com.example.simulacro.ui.UsuarioUIState


@Composable
fun PantallaInicial(
    appUIState: UsuarioUIState,
    onUsuarioObtenidos: () -> Unit,
    onUsuarioPulsado: (Usuario) -> Unit,

    //bd
    onUsuarioObtenidosBD: () -> Unit,
    onUsuarioPulsadoBD: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    when (appUIState) {
        is UsuarioUIState.Cargando -> PantallaCargando(modifier = modifier.fillMaxSize())
        is UsuarioUIState.Error -> PantallaError(modifier = modifier.fillMaxWidth())
        is UsuarioUIState.ObtenerTodosExito -> PantallaListaUsuarios(
            lista = appUIState.listaUsuarios,
            onUsuarioPulsado = onUsuarioPulsado,
            modifier = modifier.fillMaxWidth()
        )
        is UsuarioUIState.CrearExito -> onUsuarioObtenidos()
        is UsuarioUIState.ActualizarExito -> onUsuarioObtenidos()
        is UsuarioUIState.EliminarExito -> onUsuarioObtenidos()
        is UsuarioUIState.ObtenerExitoBD -> onUsuarioObtenidos()
    }
}

@Composable
fun PantallaCargando(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.ic_launcher_background),
        contentDescription = stringResource(R.string.cargando)
    )
}

@Composable
fun PantallaError(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.ic_launcher_foreground),
        contentDescription = stringResource(R.string.error)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PantallaListaUsuarios(
    lista: List<Usuario>,
    onUsuarioPulsado: (Usuario) -> Unit,
    modifier: Modifier = Modifier
) {

    Text(
        text = "Conversacion",
        modifier = Modifier.fillMaxWidth().padding(bottom = 25.dp),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineMedium
    )
    LazyColumn(modifier = modifier.padding(top = 30.dp)) {
        items(lista){ usuario ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .combinedClickable(
                        onClick = { onUsuarioPulsado(usuario) }
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Row {
                    Text(
                        text = usuario.nombre
                    )
                        Spacer(Modifier.width(12.dp))
                    Text(
                        text = usuario.telefono
                    )
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}