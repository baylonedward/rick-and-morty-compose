package com.ebaylon.rickandmortycompose.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ebaylon.rickandmortycompose.features.character.CharacterListScreen
import com.ebaylon.rickandmortycompose.features.location.LocationListScreen
import kotlinx.serialization.Serializable

/**
 * Created by: ebaylon.
 * Created on: 6/8/24.
 */
@Serializable
sealed class HomeScreens {
  @Serializable
  data object ListOfCharacters : HomeScreens()

  @Serializable
  data object ListOfLocations : HomeScreens()

  @Serializable
  data object ListOfEpisodes : HomeScreens()
}

data class TabBarItem(
  val title: String,
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector,
  val screen: HomeScreens,
  val badgeAmount: Int? = null,
)

private fun getBottomNavBarItems(): List<TabBarItem> {
  return listOf(
    TabBarItem(
      title = "Characters",
      selectedIcon = Icons.Filled.Person,
      unselectedIcon = Icons.Outlined.Person,
      screen = HomeScreens.ListOfCharacters,
      badgeAmount = 3
    ),
    TabBarItem(
      title = "Location",
      selectedIcon = Icons.Filled.Place,
      unselectedIcon = Icons.Outlined.Place,
      screen = HomeScreens.ListOfLocations
    ),
    TabBarItem(
      title = "Episode",
      selectedIcon = Icons.Filled.PlayArrow,
      unselectedIcon = Icons.Outlined.PlayArrow,
      screen = HomeScreens.ListOfEpisodes
    )
  )
}

@Composable
fun HomeScreen(
  navHostController: NavHostController,
  onCharacterSelected: (Int) -> Unit,
  onLocationSelected: (Int) -> Unit,
) {
  val tabBarItems = getBottomNavBarItems()

  Scaffold(
    bottomBar = {
      TabView(tabBarItems = tabBarItems, navController = navHostController)
    }
  ) {
    Surface(
      modifier = Modifier.padding(it)
    ) {
      NavHost(
        navController = navHostController,
        startDestination = HomeScreens.ListOfCharacters
      ) {
        composable<HomeScreens.ListOfCharacters> {
          CharacterListScreen(
            onNavigateToCharacterDetail = { characterId ->
              onCharacterSelected(characterId)
            }
          )
        }
        composable<HomeScreens.ListOfLocations> {
          LocationListScreen(
            onNavigateToLocationDetail = { locationId ->
              onLocationSelected(locationId)
            }
          )
        }
        composable<HomeScreens.ListOfEpisodes> {
          SampleScreen(text = "List of Episodes")
        }
      }
    }
  }
}

@Composable
fun TabView(tabBarItems: List<TabBarItem>, navController: NavController) {
  var selectedTabIndex by rememberSaveable {
    mutableIntStateOf(0)
  }

  NavigationBar {
    // looping over each tab to generate the views and navigation for each item
    tabBarItems.forEachIndexed { index, tabBarItem ->
      NavigationBarItem(
        selected = selectedTabIndex == index,
        onClick = {
          selectedTabIndex = index
          navController.navigate(tabBarItem.screen)
        },
        icon = {
          TabBarIconView(
            isSelected = selectedTabIndex == index,
            selectedIcon = tabBarItem.selectedIcon,
            unselectedIcon = tabBarItem.unselectedIcon,
            title = tabBarItem.title,
            badgeAmount = tabBarItem.badgeAmount
          )
        },
        label = { Text(tabBarItem.title) })
    }
  }
}

@Composable
fun TabBarIconView(
  isSelected: Boolean,
  selectedIcon: ImageVector,
  unselectedIcon: ImageVector,
  title: String,
  badgeAmount: Int? = null
) {
  BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
    Icon(
      imageVector = if (isSelected) {
        selectedIcon
      } else {
        unselectedIcon
      },
      contentDescription = title
    )
  }
}

@Composable
fun TabBarBadgeView(count: Int? = null) {
  if (count != null) {
    Badge {
      Text(count.toString())
    }
  }
}

@Composable
fun SampleScreen(text: String) {
  Column(
    modifier = Modifier
      .padding(16.dp)
      .fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(text = text)
    Spacer(modifier = Modifier.padding(vertical = 16.dp))
  }
}