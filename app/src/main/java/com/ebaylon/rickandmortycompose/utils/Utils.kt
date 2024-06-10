package com.ebaylon.rickandmortycompose.utils

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavType
import com.ebaylon.rickandmortycompose.data.remote.models.NetworkResponse
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readBytes
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.ConnectException

/**
 * Created by: ebaylon.
 * Created on: 6/10/24.
 */
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

/**
 * Created by: ebaylon.
 * Created on: 6/10/24.*
 *
 *Creates a custom NavType for serializable data classes using kotlinx.serialization.
 * This allows you to pass complex data objects as arguments in Jetpack Compose Navigation.
 * Params:
 * T - The type of the serializable data class.
 * isNullableAllowed - Whether the NavType should allow null values. Defaults to false.
 * json - The Json instance to use for serialization and deserialization.
 * Defaults to a standard Json instance.
 */
inline fun <reified T : Any> serializableType(
  isNullableAllowed: Boolean = false,
  json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
  override fun get(bundle: Bundle, key: String) =
    bundle.getString(key)?.let<String, T>(json::decodeFromString)

  override fun parseValue(value: String): T = json.decodeFromString(value)

  override fun serializeAsValue(value: T): String = json.encodeToString(value)

  override fun put(bundle: Bundle, key: String, value: T) {
    bundle.putString(key, json.encodeToString(value))
  }
}