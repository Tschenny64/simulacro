package com.example.ud5_ejemplo1.ui.pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ud5_ejemplo1.R


@Composable
fun PrimeraPantalla(
    onBotonSiguientePulsado: () -> Unit,
    onBotonAtrasPulsado: () -> Unit,
    modifier: Modifier = Modifier
){
    Column (modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(id = R.string.pantalla1),
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.weight(1F))

        Row {
            Button(
                onClick = onBotonAtrasPulsado,
                modifier = Modifier.padding(end = 10.dp)
            ) {
                Text(
                    text = stringResource(R.string.atras)
                )
            }
            Button(
                onClick = onBotonSiguientePulsado,
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(
                    text = stringResource(R.string.siguiente)
                )
            }
        }
    }
}