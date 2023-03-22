package raulalbin.prueba.tecnica

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

import dagger.hilt.android.AndroidEntryPoint
import raulalbin.prueba.tecnica.ui.navigation.CharactersTab
import raulalbin.prueba.tecnica.ui.navigation.Episodes
import raulalbin.prueba.tecnica.ui.navigation.EpisodesTab
import raulalbin.prueba.tecnica.ui.navigation.LocationsTab
import raulalbin.prueba.tecnica.ui.theme.PruebaTecnicaMCATheme
import raulalbin.prueba.tecnica.viewmodels.CharactersViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            PruebaTecnicaMCATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ScaffoldNav()
                }
            }
        }
    }
}


@Composable
fun Chars() {
    val vm: CharactersViewModel = hiltViewModel()
    val state = vm.characters.collectAsState(initial = null).value
    val loading = vm.loading.collectAsState().value
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp.times(1f)
    Log.e("Chars", "characters: " + state?.info)
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
                    backgroundColor = boxColor
                    // modifier = Modifier.size(width = 100.dp, height = 100.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
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
                            Column() {
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
//                                Text(character.location.name)
//                                Text(character.origin.name)
//                                Text(character.created)
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
            }

        }
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PruebaTecnicaMCATheme {
        Greeting("Android")
    }
}


@Composable
fun NextPageButton() {
    val viewModel: CharactersViewModel = hiltViewModel()
    var enabled = true
    val charactersState = viewModel.characters.collectAsState()
    if (charactersState.value == null || charactersState.value!!.info.next == null) {
        enabled = false
    }

    Button(
        enabled = enabled,
        onClick = {
            viewModel.nextPage()
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue),
        modifier = Modifier.padding(16.dp)
    ) {
        //Text(text = "Next", color = Color.White)
        Icon(
            painter = painterResource(id = R.drawable.ic_next),
            contentDescription = "Next Page",
            tint = Color.White
        )
    }
}


@Composable
fun PreviousPageButton() {
    val viewModel: CharactersViewModel = hiltViewModel()
    val charactersState = viewModel.characters.collectAsState()
    var enabled = true
    if (charactersState.value == null || charactersState.value!!.info.prev == null) {
        Log.e("Empty view", "EMPTY")
        enabled = false
    }

    Button(
        enabled = enabled,
        onClick = {
            viewModel.previousPage()
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue),
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_previous),
            contentDescription = "Next Page",
            tint = Color.White
        )
        //Text(text = "Previous page", color = Color.White)
    }

}

@Composable
fun EmptyView() {
}

@Composable
fun ScaffoldNav() {
    val viewModel: CharactersViewModel = hiltViewModel()
    var selectedTabIndex by remember { mutableStateOf(0) }
    Scaffold(
        topBar = {
            raulalbin.prueba.tecnica.ui.navigation.TabBarNavigation(selectedTabIndex) { index ->
                selectedTabIndex = index
            }
        },
        floatingActionButton = {
            val loadingState = viewModel.loading.collectAsState().value
            if (loadingState) {
                EmptyView()
            } else {

                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    FloatingActionButton(onClick = {
                        when (selectedTabIndex) {
                            0 -> viewModel.previousPage()
                            1 -> viewModel.previousPageEpisodes()
                            else -> viewModel.previousPage()
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_previous),
                            contentDescription = "Previous page",
                            tint = Color.White
                        )
                    }
                    FloatingActionButton(onClick = {
                        when (selectedTabIndex) {
                            0 -> viewModel.nextPage()
                            1 -> viewModel.nextPageEpisodes()
                            else -> viewModel.nextPage()
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_next),
                            contentDescription = "Next Page",
                            tint = Color.White
                        )
                    }
                }
            }

        }
    ) {
        when (selectedTabIndex) {
            0 -> CharactersTab()
            1 -> EpisodesTab()
            2 -> LocationsTab()
        }
    }
}