package raulalbin.prueba.tecnica.ui.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import raulalbin.prueba.tecnica.data.models.TvCharacter
import raulalbin.prueba.tecnica.ui.composables.Loading
import raulalbin.prueba.tecnica.viewmodels.RickAndMortyViewModel

@SuppressLint("StateFlowValueCalledInComposition")

@Composable

fun CharacterDetail(id: String, vm: RickAndMortyViewModel = hiltViewModel()) {

    Log.e("ID", "" + vm.characters.value)
    val character: TvCharacter =
        vm.characters.value!!.results.find { element -> element.id == id.toLong() }!!
    val loading = vm.loading.collectAsState().value
    val episodes = vm.characterEpisodes.collectAsState().value
    if (loading) {
        Loading()
    } else if (episodes.isEmpty()) {
        Text("Welcome to the detail")

    } else {
        Column(modifier = Modifier.fillMaxWidth(1f)) {
            AsyncImage(
                contentScale = ContentScale.FillWidth,
                model = character.image,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.45f)
            )

            Text("Episodes")

            for (episode in episodes) {
                Text("${episode.id} - ${episode.name}")
            }
        }
    }
}