package com.ebaylon.rickandmortycompose.ui.navigation

import kotlinx.serialization.Serializable

/**
 * Created by: ebaylon.
 * Created on: 6/8/24.
 */
@Serializable
sealed class Screens {
  @Serializable
  object Home

  @Serializable
  data class CharacterDetail(val id: Int)

  @Serializable
  data class LocationDetail(val id: Int)
}

@Serializable
sealed class HomeScreens {
  @Serializable
  data object ListOfCharacters : HomeScreens()

  @Serializable
  data object ListOfLocations : HomeScreens()

  @Serializable
  data object ListOfEpisodes : HomeScreens()
}