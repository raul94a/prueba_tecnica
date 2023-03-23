package raulalbin.prueba.tecnica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import raulalbin.prueba.tecnica.ui.composables.EmptyView
import raulalbin.prueba.tecnica.ui.navigation.CharactersTab
import raulalbin.prueba.tecnica.ui.navigation.EpisodesTab
import raulalbin.prueba.tecnica.ui.navigation.LocationsTab
import raulalbin.prueba.tecnica.ui.theme.PruebaTecnicaMCATheme
import raulalbin.prueba.tecnica.viewmodels.RickAndMortyViewModel

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
                    AppScaffold()
                }
            }
        }
    }
}


@Composable
fun AppScaffold() {
    val viewModel: RickAndMortyViewModel = hiltViewModel()
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

                    FloatingActionButton(

                        onClick = {
                            when (selectedTabIndex) {
                                0 -> viewModel.previousPage()
                                1 -> viewModel.previousPageEpisodes()
                                else -> viewModel.getLocationsPreviousPage()
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
                            else -> viewModel.getLocationsNextPage()
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
