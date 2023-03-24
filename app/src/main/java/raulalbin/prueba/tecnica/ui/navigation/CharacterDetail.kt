package raulalbin.prueba.tecnica.ui.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import raulalbin.prueba.tecnica.R
import raulalbin.prueba.tecnica.data.models.TvCharacter
import raulalbin.prueba.tecnica.ui.composables.EmptyView
import raulalbin.prueba.tecnica.ui.composables.Loading
import raulalbin.prueba.tecnica.ui.theme.MainDarkBlue
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
        Column(
            modifier = Modifier.fillMaxWidth(1f).background(color= MainDarkBlue),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val titleColor = Color.White
            val textColor = Color.White
            val titleSize = 17.sp
            val config = LocalConfiguration.current


            Spacer(modifier = Modifier.height(20.dp))
            AsyncImage(


                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(250.dp)
                    .height(250.dp)
                    .clip(
                        CircleShape
                    ),
                model = character.image,
                contentDescription = "Picture of ${character.name}",

                )
            Spacer(modifier = Modifier.height(20.dp))

            Column {


                Row(
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {

                    Text(
                        "Name",
                        color = titleColor,
                        fontSize = titleSize,
                        modifier = Modifier.fillMaxWidth(0.5f),
                        overflow = TextOverflow.Clip
                    )
                    Text(character.name, color = textColor, fontSize = titleSize)
                    Spacer(modifier = Modifier.width(10.dp))
                    when (character.gender) {
                        "Male" -> Icon(
                            painter = painterResource(id = R.drawable.male_svgrepo_com),
                            contentDescription = "aa",
                            modifier = Modifier.size(height = 20.dp, width = 20.dp),
                            tint = Color.hsl(207f, 0.83f, 0.65f)
                        )
                        "Female" -> Icon(
                            painter = painterResource(id = R.drawable.female_svgrepo_com),
                            contentDescription = "aa",
                            modifier = Modifier.size(height = 20.dp, width = 20.dp),
                            tint = Color.Magenta

                        )
                        else -> EmptyView()
                    }
                }
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        "Species",
                        color = titleColor,
                        fontSize = titleSize,
                        modifier = Modifier.fillMaxWidth(0.5f),
                    )
                    Text(character.species, color = textColor, fontSize = titleSize)

                }
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        "Status",
                        color = titleColor,
                        fontSize = titleSize,
                        modifier = Modifier.fillMaxWidth(0.5f),
                    )

                    Text(character.status, color = textColor, fontSize = titleSize)
                    Spacer(modifier = Modifier.width(10.dp))
                    when (character.status) {
                        "Alive" -> Icon(
                            painter = painterResource(id = R.drawable.ic_circle),
                            contentDescription = "aa",
                            tint = Color.Green
                        )
                        "Dead" -> Icon(
                            painter = painterResource(id = R.drawable.ic_circle),
                            contentDescription = "aa",
                            tint = Color.Red
                        )
                        else -> EmptyView()
                    }

                }
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        "Origin",
                        color = titleColor,
                        fontSize = titleSize,
                        modifier = Modifier.fillMaxWidth(0.5f),
                    )
                    Text(character.origin.name, color = textColor, fontSize = titleSize)

                }
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        "Location",
                        color = titleColor,
                        fontSize = titleSize,
                        modifier = Modifier.fillMaxWidth(0.5f),
                    )
                    Text(character.location.name, color = textColor, fontSize = titleSize)

                }

                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        "First apparition",
                        color = titleColor,
                        fontSize = titleSize,
                        modifier = Modifier.fillMaxWidth(0.5f),
                    )
                    Text(
                        character.firstEpisode?.name ?: "Unknown",
                        color = textColor,
                        fontSize = titleSize,
                        overflow = TextOverflow.Ellipsis
                    )
                }



                Spacer(modifier = Modifier.height(20.dp))
                Text("Episodes", color = textColor, fontSize = titleSize)
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(1f).fillMaxWidth(0.9f),
                    contentPadding = PaddingValues(5.dp),
                    verticalArrangement = Arrangement.spacedBy(3.dp)


                )
                {

                    items(
                        items = episodes,
                        key = { character -> character.id },

                        ) { episode ->
                        Text(
                            "${episode.id} - ${episode.name}",
                            color = textColor,
                            fontSize = titleSize
                        )

                    }

                }
            }
        }
    }
}