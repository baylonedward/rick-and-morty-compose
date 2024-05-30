package com.ebaylon.rickandmortycompose

import com.ebaylon.rickandmortycompose.data.remote.ApiClient
import com.ebaylon.rickandmortycompose.data.remote.models.ApiEndPoints
import com.ebaylon.rickandmortycompose.di.HttpClientModule
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * [ApiClient] unit test.
 */
class ApiClientUnitTest {

  private val httpClientModule = HttpClientModule()
  private val apiClient = ApiClient(httpClientModule.provideInternetClient())

  @Test
  fun api_is_up_and_the_same_as_expected() {
    runBlocking {
      apiClient.getApiEndPoints().apply {
        assertEquals(ApiEndPoints.expectedTestResult, this.result)
      }
    }
  }
}