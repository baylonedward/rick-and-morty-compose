package com.ebaylon.rickandmortycompose.data.remote.models.character

import kotlinx.serialization.Serializable

/**
 * Created by: ebaylon.
 * Created on: 5/31/24.
 */
@Serializable
data class Character(
  val id: Int,
  val name: String,
  val status: String,
  val species: String,
  val type: String,
  val gender: String,
  val origin: Origin,
  val location: Location,
  val image: String,
  val episode: List<String>,
  val url: String,
  val created: String
) {

  enum class Status(val value: String) {
    ALIVE("Alive"),
    DEAD("Dead"),
    UNKNOWN("unknown")
  }

  enum class Gender(val value: String) {
    FEMALE("Female"),
    MALE("Male"),
    GENDERLESS("Genderless"),
    UNKNOWN("unknown")
  }
}

@Serializable
data class Origin(
  val name: String,
  val url: String
)

@Serializable
data class Location(
  val name: String,
  val url: String
)
