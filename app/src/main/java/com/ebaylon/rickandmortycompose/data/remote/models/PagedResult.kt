package com.ebaylon.rickandmortycompose.data.remote.models

import kotlinx.serialization.Serializable

/**
 * Created by: ebaylon.
 * Created on: 5/31/24.
 */
@Serializable
data class PagedResult<T> (
  val info: Info,
  val results: List<T>
)

@Serializable
data class Info(
  val count: Int,
  val pages: Int,
  val next: String?,
  val prev: String?
)