package com.ebaylon.rickandmortycompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.ebaylon.rickandmortycompose.features.character.CharacterDetailScreen
import com.ebaylon.rickandmortycompose.features.home.HomeScreen
import com.ebaylon.rickandmortycompose.features.location.LocationDetailScreen
import com.ebaylon.rickandmortycompose.ui.navigation.Screens
import com.ebaylon.rickandmortycompose.ui.navigation.Transitions
import com.ebaylon.rickandmortycompose.ui.theme.RickAndMortyComposeTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      // main nav controller
      val navController = rememberNavController()
      // bottom nav bar's own nav controller
      val homeNavController = rememberNavController()

      RickAndMortyComposeTheme {
        NavHost(
          navController = navController,
          graph = getNavGraph(
            navController = navController,
            homeNavController = homeNavController
          ),
          enterTransition = { Transitions.slideInFromRight() },
          exitTransition = { Transitions.fadeOut() },
          popExitTransition = { Transitions.slideOutTowardsRight() },
          popEnterTransition = { Transitions.slideInFromLeft() }
        )
      }
    }
  }

  private fun getNavGraph(
    navController: NavHostController,
    homeNavController: NavHostController
  ): NavGraph {
    return navController.createGraph(startDestination = Screens.Home) {
      composable<Screens.Home> {
        HomeScreen(
          navHostController = homeNavController,
          onCharacterSelected = { characterId ->
            navController.navigate(Screens.CharacterDetail(characterId))
          },
          onLocationSelected = { locationId ->
            navController.navigate(Screens.LocationDetail(locationId))
          }
        )
      }
      composable<Screens.CharacterDetail> {
        CharacterDetailScreen(
          onBackClick = { navController.popBackStack() }
        )
      }
      composable<Screens.LocationDetail> {
        LocationDetailScreen(
          onBackClick = { navController.popBackStack() }
        )
      }
    }
  }
}