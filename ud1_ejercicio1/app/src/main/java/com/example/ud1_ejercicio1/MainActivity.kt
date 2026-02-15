package com.example.ud1_ejercicio1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ud1_ejercicio1.ui.theme.Ud1_ejercicio1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ud1_ejercicio1Theme {
                Greeting(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    var indice by remember { mutableStateOf(0) }
    val imagenes = listOf(
        R.drawable.imagen1,
        R.drawable.imagen2,
        R.drawable.imagen3
    )

    Column(modifier = modifier.padding(16.dp)) {
        Image(
            painter = painterResource(id = imagenes[indice]),
            contentDescription = "Imagen actual"
        )

        Button(
            onClick = { indice = (indice + 1) % imagenes.size },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Cambiar imagen")
        }
    }
}
