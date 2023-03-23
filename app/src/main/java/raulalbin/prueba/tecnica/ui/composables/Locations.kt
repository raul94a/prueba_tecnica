package raulalbin.prueba.tecnica.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import raulalbin.prueba.tecnica.R
import raulalbin.prueba.tecnica.data.models.Location
import raulalbin.prueba.tecnica.ui.theme.MainDarkBlue
import raulalbin.prueba.tecnica.viewmodels.RickAndMortyViewModel

@Composable
fun Locations() {
    val vm: RickAndMortyViewModel = hiltViewModel()
    val state = vm.locations.collectAsState(initial = null).value
    val loading = vm.loading.collectAsState().value
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp.times(1f)
    if (loading) {
       Loading()
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
                    backgroundColor = MainDarkBlue,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp)
                ) {

                LocationsInfo(location = location)
                }
            }
        }
    }

}

@Composable
fun LocationsInfo(location: Location){
    val titleColor = Color.hsl(230f, 0.45f, 0.77f)
    val textColor = Color.White
    val titleSize = 17.sp
    val textSize = 15.sp
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly

    ) {
        val iconsMap = location.mapTypeWithDrawable()
        val drawable: Int = iconsMap[location.type] ?: R.drawable.ic_planet
        Icon(
            painter = painterResource(id = drawable),
            contentDescription = "Icon for the location type",
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .padding(8.0.dp),
            tint = Color.White
        )



        Column(modifier = Modifier
            .padding(12.0.dp)
            .fillMaxWidth(0.6f)) {
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