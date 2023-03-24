package raulalbin.prueba.tecnica.ui.navigation


import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import raulalbin.prueba.tecnica.Home
import raulalbin.prueba.tecnica.Main
import raulalbin.prueba.tecnica.viewmodels.RickAndMortyViewModel


@Composable
fun NavHost(navController: NavHostController) {
    val vm : RickAndMortyViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.home
    ) {
        composable(NavigationRoutes.home) {

            Home(navController,vm)
        }
        composable(NavigationRoutes.character) { navBackstackEntry ->
            var characterId = navBackstackEntry.arguments?.getString("id")
            Log.e("Character Id", "" + characterId)


            vm.getCharacter(characterId!!)

            CharacterDetail(characterId,vm)
        }
        composable("Screen 3") {
            Text("Screen 3")
        }
    }
}

class NavigationRoutes {
    companion object {
        const val home = "home"
        val character = "character/{id}"

    }

}