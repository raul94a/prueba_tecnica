package raulalbin.prueba.tecnica.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration

import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

import raulalbin.prueba.tecnica.viewmodels.CharactersViewModel

@Composable
fun EpisodesTab() {
    val viewModel: CharactersViewModel = hiltViewModel();
    viewModel.getEpisodes();
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


@Composable
fun Episodes() {
    val vm: CharactersViewModel = hiltViewModel()
    val state = vm.episodes.collectAsState(initial = null).value
    val loading = vm.loading.collectAsState().value
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp.times(1f)
    Log.e("Chars", "pagination: " + state?.info)
    val boxColor = Color.hsl(230f, 0.34f, 0.17f)
    val titleColor = Color.hsl(230f, 0.45f, 0.77f)
    val textColor = Color.White
    val titleSize = 17.sp
    val textSize = 15.sp
    if (loading) {
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

                color = Color.Magenta
            )
        }
    } else if (state == null) {
        Text(text = "There are no episodes to show")
    } else {
        LazyColumn(
            modifier = Modifier.height(screenHeight),
            contentPadding = PaddingValues(5.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)


        )
        {

            items(
                items = state.results,
                key = { episode -> episode.id },

                ) { episode ->
                //Text(character.name)
                Card(
                    modifier = Modifier
                        .height(height = screenHeight.times(0.3f))
                        .fillMaxWidth(),
                    backgroundColor = boxColor
                    // modifier = Modifier.size(width = 100.dp, height = 100.dp)
                ) {


                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(6.dp)
                    ) {
                        Column() {
                            Text(
                                "Episode name",
                                color = titleColor,
                                fontSize = titleSize,
                                overflow = TextOverflow.Clip
                            )

                            Text(episode.name, color = textColor, fontSize = textSize)


                            Text("Air date", color = titleColor, fontSize = titleSize)
                            Text(
                                episode.airDate ?: "Unknown",
                                color = textColor,
                                fontSize = textSize
                            )
                            Text("Number of characters", color = titleColor, fontSize = titleSize)
                            Text(
                                episode.characters.size.toString(),
                                color = textColor,
                                fontSize = textSize
                            )
                            Row(

                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                Spacer(modifier = Modifier.width(5.dp))
                                for (image in episode.charactersImages) {
                                    AsyncImage(

                                        model = image,
                                        contentScale = ContentScale.Crop,
                                        contentDescription = "",
                                        modifier = Modifier
                                            .width(60.dp)
                                            .height(60.dp)
                                            .clip(
                                                CircleShape
                                            )
                                    )
                                }
                                Spacer(modifier = Modifier.width(5.dp))

                            }


//

                        }
                    }


                }

            }

        }
    }

}