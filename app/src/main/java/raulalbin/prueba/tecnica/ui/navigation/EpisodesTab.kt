package raulalbin.prueba.tecnica.ui.navigation


import androidx.compose.foundation.layout.*

import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.verticalScroll

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalConfiguration

import androidx.compose.ui.unit.dp

import androidx.hilt.navigation.compose.hiltViewModel

import raulalbin.prueba.tecnica.ui.composables.Episodes

import raulalbin.prueba.tecnica.viewmodels.RickAndMortyViewModel

@Composable
fun EpisodesTab() {
    val viewModel: RickAndMortyViewModel = hiltViewModel()
    viewModel.getEpisodes()
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp.times(1f)
    Box(modifier = Modifier.height(screenHeight)) {

        Column(
            Modifier

                .verticalScroll(enabled = true, state = rememberScrollState())
        ) {
            Episodes()
            Spacer(modifier = Modifier.height(100.dp))


        }
    }
}

