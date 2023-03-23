package raulalbin.prueba.tecnica.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import raulalbin.prueba.tecnica.ui.theme.MainDarkBlue

@Composable
fun Loading(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {

        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.Center),

            color = MainDarkBlue
        )
    }
}