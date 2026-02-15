package com.example.retrofit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retrofit.modelo.Respuesta
import com.example.retrofit.ui.StarwarsUIState
import com.example.retrofit.ui.StarWarsViewModel
import com.example.retrofit.ui.theme.RetrofitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RetrofitTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaDatos(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun PantallaDatos(
    modifier: Modifier = Modifier,
    viewModel: StarWarsViewModel = viewModel()
) {
    val starwarsUIState = viewModel.starwarsUIState

    when (starwarsUIState) {
        is StarwarsUIState.Cargando -> PantallaCargando(modifier = Modifier.fillMaxSize())
        is StarwarsUIState.Exito -> PantallaExito(
            respuesta = starwarsUIState.respuesta,
            modifier = modifier.fillMaxWidth()
        )
        is StarwarsUIState.Error -> PantallaError(modifier = Modifier.fillMaxWidth())
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
        painter = painterResource(R.drawable.ic_launcher_background),
        contentDescription = stringResource(R.string.error_de_conexion)
    )
}

@Composable
fun PantallaExito(respuesta: Respuesta, modifier: Modifier = Modifier) {
    LazyColumn(modifier = Modifier) {
        items(respuesta.resultados) {
            personaje ->
            Box(
                modifier = Modifier.padding(8.dp)
            ) {
                Column(modifier = modifier.fillMaxWidth()) {
                    Text(
                        text = personaje.nombre
                    )
                    Text(
                        text = personaje.genero
                    )
                    HorizontalDivider()
                }
            }
        }
    }

}
