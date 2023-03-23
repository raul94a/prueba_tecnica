package raulalbin.prueba.tecnica.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import raulalbin.prueba.tecnica.viewmodels.RickAndMortyViewModel


@Composable
fun TabBarNavigation(selectedTabIndex: Int, onClick: (index: Int) -> Unit) {
    var query: String by rememberSaveable { mutableStateOf("") }
    val tabs = listOf("Characters", "Episodes", "Locations")
    val viewModel : RickAndMortyViewModel = hiltViewModel()
    val boxColor = Color.hsl(230f, 0.34f, 0.17f)

    TopAppBar(
        backgroundColor = boxColor,
        modifier = Modifier.height(160.dp).background(boxColor)) {

        Column(verticalArrangement = Arrangement.SpaceBetween) {

            Text(
                text = "Rick and Morty APP",
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.padding(10.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row {

                TextField(
                    modifier = Modifier
                        .fillMaxWidth(0.8f).border(1.dp, Color.White, RoundedCornerShape(10.dp)),
                    value = query,

                    onValueChange = {
                        query = it
                    },
                    maxLines = 1,
                    textStyle = MaterialTheme.typography.subtitle1.copy(Color.White),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),

                    colors = TextFieldDefaults.textFieldColors(

                        disabledTextColor = Color.Transparent,
                    cursorColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    )
                )
                IconButton(onClick = {
                    when(selectedTabIndex){
                        0 -> viewModel.searchCharacters(query)
                        1 -> viewModel.searchEpisodes(query)
                        else -> viewModel.searchLocations(query)
                    }
                    query = ""
                }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search", tint= Color.White)
                }
            }
            TabRow(backgroundColor = boxColor,selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { onClick(index) },
                        text = { Text(title, color = Color.White) }
                    )
                }
            }
        }
    }
    // content of each tab goes here

}