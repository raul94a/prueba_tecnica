package raulalbin.prueba.tecnica.ui.navigation

import raulalbin.prueba.tecnica.R

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

import raulalbin.prueba.tecnica.viewmodels.CharactersViewModel

@Composable
fun LocationsTab() {
    val viewModel: CharactersViewModel = hiltViewModel();
    viewModel.getLocations();
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp.times(1f)
    Box(modifier = Modifier.height(screenHeight)) {

        Column(
            Modifier

                .verticalScroll(enabled = true, state = rememberScrollState())
        ) {
            Locations()
            Spacer(modifier = Modifier.height(100.dp))


        }
    }
}


@Composable
fun Locations() {
    val vm: CharactersViewModel = hiltViewModel()
    val state = vm.locations.collectAsState(initial = null).value
    val loading = vm.loading.collectAsState().value
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp.times(1f)
    Log.e("Chars", "characters: " + state?.results)
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
        Text(text = "There are no locations to show")
    } else {
        LazyColumn(
            modifier = Modifier.height(screenHeight),
            contentPadding = PaddingValues(5.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)


        )
        {

            items(
                items = state.results,
                key = { location -> location.id },

                ) { location ->

                Card(
                    shape = RoundedCornerShape(12.dp),
                    backgroundColor = boxColor,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center

                    ) {
                        val iconsMap = location.mapTypeWithDrawable()
                        val drawable: Int = iconsMap[location.type] ?: R.drawable.ic_planet
                        Icon(
                            painter = painterResource(id = drawable),
                            contentDescription = "Icon for the location type",
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp).padding(8.0.dp),
                            tint = Color.White
                        )

                        Spacer(Modifier.width(50.dp))

                        Column(modifier = Modifier.padding(12.0.dp)) {
                            Text(
                                "Location name",
                                color = titleColor,
                                fontSize = titleSize,

                                overflow = TextOverflow.Clip,

                                )
                            Text(location.name, color = textColor, fontSize = textSize)
                            Text("Location type", color = titleColor, fontSize = titleSize)
                            Text(location.type, color = textColor, fontSize = textSize)
                            Text("Dimension", color = titleColor, fontSize = titleSize)
                            Text(location.dimension, color = textColor, fontSize = textSize)
                            Text("Residents", color = titleColor, fontSize = titleSize)
                            Text(
                                location.residents.size.toString(),
                                color = textColor,
                                fontSize = textSize
                            )

                        }
                    }

                }
            }
        }
    }

}
