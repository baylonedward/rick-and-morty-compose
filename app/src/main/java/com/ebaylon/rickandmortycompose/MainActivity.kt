package com.ebaylon.rickandmortycompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.ebaylon.rickandmortycompose.features.character.CharacterDetailScreen
import com.ebaylon.rickandmortycompose.features.character.CharacterListScreen
import com.ebaylon.rickandmortycompose.features.home.HomeScreen
import com.ebaylon.rickandmortycompose.ui.navigation.Screens
import com.ebaylon.rickandmortycompose.ui.theme.RickAndMortyComposeTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {

  @Serializable
  object Profile

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val navController = rememberNavController()

      RickAndMortyComposeTheme {
        NavHost(
          navController = navController,
          graph = getNavGraph(navController)
        )
      }
    }
  }

  private fun getNavGraph(navController: NavController): NavGraph {

    return navController.createGraph(startDestination = Screens.Home) {
      composable<Screens.Home> {
        HomeScreen(
          onNavigateToCharacterDetail = { navController.navigate(route = Screens.CharacterDetail(0)) }
        )
      }
      composable<Screens.ListOfCharacters> {
        CharacterListScreen()
      }
      composable<Screens.CharacterDetail> {
        CharacterDetailScreen()
      }
    }

  }
}