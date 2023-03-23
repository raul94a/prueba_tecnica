package raulalbin.prueba.tecnica.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
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
import raulalbin.prueba.tecnica.data.models.Episode
import raulalbin.prueba.tecnica.ui.theme.MainDarkBlue
import raulalbin.prueba.tecnica.viewmodels.RickAndMortyViewModel

@Composable
fun Episodes() {
    val vm: RickAndMortyViewModel = hiltViewModel()
    val state = vm.episodes.collectAsState(initial = null).value
    val loading = vm.loading.collectAsState().value
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp.times(1f)

    if (loading) {
       Loading()
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

                Card(
                    modifier = Modifier
                        .height(height = screenHeight.times(0.3f))
                        .fillMaxWidth(),
                    backgroundColor = MainDarkBlue

                ) {
                    EpisodesInformation(episode = episode)

                }

            }

        }
    }

}

@Composable
fun EpisodesInformation(episode: Episode) {

    val titleColor = Color.hsl(230f, 0.45f, 0.77f)
    val textColor = Color.White
    val titleSize = 17.sp
    val textSize = 15.sp
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp)
    ) {
        Column {
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
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
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

        }
    }

}