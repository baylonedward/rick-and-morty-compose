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
  private val apiClient = ApiClient(httpClientModule.provideRickAndMortyClient())

  @Test
  fun api_is_up_and_the_same_as_expected() {
    runBlocking {
      apiClient.getApiEndPoints().apply {
        assertEquals(ApiEndPoints.expectedTestResult, this.result)
      }
    }
  }

  @Test
  fun get_first_page_of_characters() {
    runBlocking {
      apiClient.getCharacters().apply {
        // 20 characters per page
        assertEquals(20, this.result?.results?.size)
        // First page
        assertEquals(null, this.result?.info?.prev)
        // Second page
        assertEquals("https://rickandmortyapi.com/api/character?page=2", this.result?.info?.next)
      }
    }
  }


  @Test
  fun get_characters_by_ids() {
    val idsArray = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)

    runBlocking {
      apiClient.getCharactersById(ids = idsArray).apply {
        assertEquals(20, this.result?.size)
      }
    }
  }
  @Test
  fun get_characters_page_by_url() {
    val secondPageUrl = "https://rickandmortyapi.com/api/character?page=2"
    runBlocking {
      apiClient.getCharactersByPageUrl(secondPageUrl).apply {
        // 20 characters per page
        assertEquals(20, this.result?.results?.size)
        // First page
        assertEquals("https://rickandmortyapi.com/api/character?page=1", this.result?.info?.prev)
        // Second page
        assertEquals("https://rickandmortyapi.com/api/character?page=3", this.result?.info?.next)
      }
    }
  }
}