package com.ebaylon.rickandmortycompose.utils

import android.util.Log
import com.ebaylon.rickandmortycompose.data.remote.models.NetworkResponse
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readBytes
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json
import java.net.ConnectException

/**
 * Created by: ebaylon.
 * Created on: 2/9/22.
 *
 * Helper method to evaluate network call using Ktor.
 *
 * Type:
 * 1. T : Generic - should be the expected data model for the network call response
 *
 * Parameters:
 * 1. call = { () -> HttpResponse } - a lambda that contains a method which returns [HttpResponse]
 */
suspend inline fun <reified T> evaluateRequest(call: () -> suspend () -> HttpResponse): NetworkResponse<T> {
  return try {
    // invoke call
    val httpResponse = call().invoke()
    // decode string to provided data model
    val result = httpResponse.body<T>()

    // return success
    NetworkResponse(
      result = result,
      statusCode = httpResponse.status,
      errorMessage = null
    )
  } catch (e: Exception) {
    // log exception
    Log.e("NetworkException:", e.toString())

    var result: String? = null

    // check exception type
    val statusCode: HttpStatusCode = when (e) {
      is ClientRequestException -> {
        // try to read error response
        result = e.response.readBytes()
          .decodeToString()
          .let {
            try {
              val json = Json {
                ignoreUnknownKeys = true
              }
              json.decodeFromString<String>(it)
            } catch (e: java.lang.Exception) {
              Log.e("Exception:", e.toString())
              null
            }
          }
        e.response.status
      }

      is ServerResponseException -> {
        e.response.status
      }

      is RedirectResponseException -> {
        e.response.status
      }

      is ConnectException -> {
        // failed to connect
        HttpStatusCode.NotFound
      }

      is HttpRequestTimeoutException -> {
        HttpStatusCode.RequestTimeout
      }

      else -> {
        HttpStatusCode.BadRequest
      }
    }

    // return with exception
    NetworkResponse(
      result = null,
      statusCode = statusCode,
      errorMessage = e.message,
      errorResponse = result
    )
  }
}
