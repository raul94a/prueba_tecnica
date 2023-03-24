package raulalbin.prueba.tecnica.ui.navigation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import raulalbin.prueba.tecnica.ui.composables.Locations

import raulalbin.prueba.tecnica.viewmodels.RickAndMortyViewModel

@Composable
fun LocationsTab( viewModel : RickAndMortyViewModel= hiltViewModel()) {


    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp.times(1f)
    Box(modifier = Modifier.height(screenHeight)) {

        Column(
            Modifier

                .verticalScroll(enabled = true, state = rememberScrollState())
        ) {
            Locations()
            Spacer(modifier = Modifier.height(100.dp))


        }
    }
}


