package com.ebaylon.rickandmortycompose.data.remote

import com.ebaylon.rickandmortycompose.data.remote.models.ApiEndPoints
import com.ebaylon.rickandmortycompose.di.HttpClientModule
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by: ebaylon.
 * Created on: 5/30/24.
 */
@Singleton
class ApiClient @Inject constructor(
  @HttpClientModule.InternetClient private val client: HttpClient
){

  suspend fun getApiEndPoints(): NetworkResponse<ApiEndPoints> =
    evaluateRequest<ApiEndPoints> {
      suspend { client.get("") }
    }

  suspend fun getCharacters(): NetworkResponse<String> = evaluateRequest<String> {
    suspend { client.get("/character") }
  }
}