package raulalbin.prueba.tecnica.ui.navigation
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import raulalbin.prueba.tecnica.Chars
import raulalbin.prueba.tecnica.EmptyView
import raulalbin.prueba.tecnica.viewmodels.CharactersViewModel

@Composable
fun CharactersTab() {
    val viewModel: CharactersViewModel = hiltViewModel();
    viewModel.getCharacters()
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp.times(1f)
    Box(modifier = Modifier.height(screenHeight)) {

        Column(
            Modifier

                .verticalScroll(enabled = true, state = rememberScrollState())
        ) {
            Chars()
            Spacer(modifier = Modifier.height(100.dp))



        }
    }
}

