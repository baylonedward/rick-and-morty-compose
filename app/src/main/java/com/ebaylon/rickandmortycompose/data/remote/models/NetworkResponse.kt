package com.ebaylon.rickandmortycompose.data.remote.models

import io.ktor.http.HttpStatusCode

/**
 * Created by: ebaylon.
 * Created on: 2/9/22.
 *
 * Data class to encapsulate NetworkResponse result with additional information like
 *
 * 1. statusCode: [HttpStatusCode]
 * 2. errorMessage: String
 * 3. errorResponse: String
 */
data class NetworkResponse<T>(
  val result: T?,
  val statusCode: HttpStatusCode? = null,
  val errorMessage: String? = null,
  val errorResponse: String? = null
)
