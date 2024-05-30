package com.ebaylon.rickandmortycompose.data.remote

import io.ktor.http.HttpStatusCode

/**
 * Created by: ebaylon.
 * Created on: 2/9/22.
 *
 * Data class to encapsulate NetworkResponse with additional information like
 *
 * 1. statusCode: [HttpStatusCode]
 * 2. errorMessage: String
 */
data class NetworkResponse<T>(
    val result: T?,
    val statusCode: HttpStatusCode? = null,
    val errorMessage: String? = null,
    val errorResponse: String? = null
)
