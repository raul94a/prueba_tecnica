package raulalbin.prueba.tecnica.ui.composables
import androidx.compose.foundation.clickable
import raulalbin.prueba.tecnica.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import raulalbin.prueba.tecnica.data.models.TvCharacter
import raulalbin.prueba.tecnica.di.NavigationModule
import raulalbin.prueba.tecnica.ui.theme.MainDarkBlue
import raulalbin.prueba.tecnica.viewmodels.RickAndMortyViewModel


@Composable
fun Characters(navController: NavHostController, vm : RickAndMortyViewModel) {
    val state = vm.characters.collectAsState(initial = null).value
    val loading = vm.loading.collectAsState().value
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp.times(1f)


    if (loading) {
       Loading()
    } else if (state == null) {
        Text(text = "There are no characters to show")
    } else {

        LazyColumn(
            modifier = Modifier.height(screenHeight),
            contentPadding = PaddingValues(5.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)


        )
        {

            items(
                items = state.results,
                key = { character -> character.id },

                ) { character ->
                //Text(character.name)
                Card(
                    modifier = Modifier
                        .height(height = screenHeight.times(0.3f))
                        .fillMaxWidth(),
                    backgroundColor = MainDarkBlue
                    // modifier = Modifier.size(width = 100.dp, height = 100.dp)
                ) {
                    CharacterInformation(character = character, navController = navController)
                }
            }

        }
    }

}

@Composable
fun CharacterInformation(character: TvCharacter,navController: NavHostController){
    val titleColor = Color.hsl(230f, 0.45f, 0.77f)
    val textColor = Color.White
    val titleSize = 17.sp
    val textSize = 15.sp
    val config = LocalConfiguration.current
   
    val screenHeight = config.screenHeightDp.dp.times(1f)
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.clickable {
       navController.navigate("character/${character.id}")
    }) {
        AsyncImage(

            modifier = Modifier
                .width(150.dp)
                .height(height = screenHeight.times(0.3f)),
            contentScale = ContentScale.FillHeight,
            model = character.image,
            contentDescription = "Picture of ${character.name}",

            )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp)
        ) {
            Column {
                Text(
                    "Name",
                    color = titleColor,
                    fontSize = titleSize,
                    modifier = Modifier.fillMaxWidth(0.5f),
                    overflow = TextOverflow.Clip
                )
                Row {
                    Text(character.name, color = textColor, fontSize = textSize)
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

                Text("Species", color = titleColor, fontSize = titleSize)
                Text(character.species, color = textColor, fontSize = textSize)
                Text("Status", color = titleColor, fontSize = titleSize)
                Row {
                    Text(character.status, color = textColor, fontSize = textSize)
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
                Text("First apparition", color = titleColor, fontSize = titleSize)
                Text(
                    character.firstEpisode?.name ?: "Unknown",
                    color = textColor,
                    fontSize = textSize,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }


    }
}