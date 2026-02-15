package com.example.ud1_ejercicio2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ud1_ejercicio2.ui.theme.Ud1_ejercicio2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ud1_ejercicio2Theme {
                Greeting(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    var precioTexto by remember { mutableStateOf("") }
    var ivaSeleccionado by remember { mutableStateOf(0.03f) }
    var precioFinal by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = precioTexto,
            onValueChange = { precioTexto = it },
            label = { Text("Precio del producto") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = ivaSeleccionado == 0.21f,
                onClick = { ivaSeleccionado = 0.21f }
            )
            Text(text = "21%")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = ivaSeleccionado == 0.10f,
                onClick = { ivaSeleccionado = 0.10f }
            )
            Text(text = "10%")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = ivaSeleccionado == 0.03f,
                onClick = { ivaSeleccionado = 0.03f }
            )
            Text(text = "3%")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val precio = precioTexto.toFloatOrNull()
            if (precio != null) {
                val total = precio * (1 + ivaSeleccionado)
                precioFinal = "Precio: $${"%.2f".format(total)}"
            } else {
                precioFinal = "Introduce un número válido"
            }
        }) {
            Text("Calcula")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = precioFinal)
    }
}
