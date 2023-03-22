package raulalbin.prueba.tecnica.ui.navigation

import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import raulalbin.prueba.tecnica.EmptyView


@Composable
fun TabBarNavigation(selectedTabIndex: Int, onClick: (index: Int) -> Unit) {
    val tabs = listOf("Characters", "Episodes", "Locations")
    TopAppBar(modifier = Modifier.height(100.dp)) {

        Column(verticalArrangement = Arrangement.SpaceBetween) {

            Text(text = "Rick and Morty APP", fontSize = 24.sp, color = Color.White, modifier = Modifier.padding(10.dp))
            Spacer(modifier = Modifier.height(10.dp))
            TabRow(selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { onClick(index) },
                        text = { Text(title) }
                    )
                }
            }
        }
    }
    // content of each tab goes here

}