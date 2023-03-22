package raulalbin.prueba.tecnica

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import dagger.hilt.android.AndroidEntryPoint
import raulalbin.prueba.tecnica.ui.theme.PruebaTecnicaMCATheme
import raulalbin.prueba.tecnica.viewmodels.CharactersViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val vm: CharactersViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.getCharacters()

        setContent {
            PruebaTecnicaMCATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        Chars()
                        Row {
                            PreviousPageButton()
                            NextPageButton()
                        }

                    }
                }
            }
        }
    }
}


@Composable
fun Chars() {
    val vm: CharactersViewModel = hiltViewModel()
    var state = vm.characters.collectAsState(initial = null).value
    var loading = vm.loading.collectAsState().value


    if (loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), contentAlignment = Alignment.Center
        ) {

            CircularProgressIndicator(
                modifier = Modifier.size(50.dp),

                color = Color.Magenta
            )
        }
    } else if (state == null) {
        Text(text = "There are no characters to show")
    } else {
        LazyColumn()
        {

            items(
                items = state.results,
                key = { character -> character.id }
            ) { character ->
                Text(character.name)
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
            Text(text = "Next", color = Color.White)
            Icon(
                painter = painterResource(id = R.drawable.ic_next),
                contentDescription = "Next Page",
                tint = Color.White
            )
        }
    }


@Composable
fun PreviousPageButton() {
    val viewModel: CharactersViewModel  =  hiltViewModel()
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
            Text(text = "Previous page", color = Color.White)
        }

}

@Composable
fun EmptyView() {
}