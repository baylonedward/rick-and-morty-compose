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
  object ListOfCharacters

  @Serializable
  data class CharacterDetail(val id: Int)
}