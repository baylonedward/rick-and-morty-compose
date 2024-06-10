package com.ebaylon.rickandmortycompose.data.remote.models

import kotlinx.serialization.Serializable

/**
 * Created by: ebaylon.
 * Created on: 5/30/24.
 */
@Serializable
data class ApiEndPoints(
  val characters: String,
  val locations: String,
  val episodes: String
) {
  companion object {
    val expectedTestResult = ApiEndPoints(
      characters = "https://rickandmortyapi.com/api/character",
      locations = "https://rickandmortyapi.com/api/location",
      episodes = "https://rickandmortyapi.com/api/episode"
    )
  }
}
