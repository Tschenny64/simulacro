package com.example.ud5_ejemplo3.ui.pantallas

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.KeyboardType
import com.example.ud5_ejemplo3.R
@Composable
fun PantallaInicio(
    onBotonSiguientePulsado: (String) -> Unit,
    modifier: Modifier = Modifier
){
    var numero by remember { mutableStateOf("") }

    Column (modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(id = R.string.inicio),
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.weight(1F))

        TextField(
            value = numero,
            label = { Text(text = stringResource(R.string.numero_1))},
            onValueChange = {numero = it},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.weight(1F))

        BotonSiguiente(onClick = {onBotonSiguientePulsado(numero)})

    }
}

@Composable
fun BotonSiguiente(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Button(onClick = onClick) {
        Text(
            text = stringResource(R.string.siguiente)
        )
    }
}