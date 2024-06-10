package com.ebaylon.rickandmortycompose.data.remote

import com.ebaylon.rickandmortycompose.data.remote.models.ApiEndPoints
import com.ebaylon.rickandmortycompose.data.remote.models.NetworkResponse
import com.ebaylon.rickandmortycompose.data.remote.models.PagedResult
import com.ebaylon.rickandmortycompose.data.remote.models.character.Character
import com.ebaylon.rickandmortycompose.di.HttpClientModule
import com.ebaylon.rickandmortycompose.utils.evaluateRequest
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
  @HttpClientModule.RickAndMortyClient private val client: HttpClient
) {

  /**
   * Get the paged character result
   *
   * Will return a paged result of characters
   **/
  private suspend fun getPagedCharacterResult(url: String): NetworkResponse<PagedResult<Character>> =
    evaluateRequest<PagedResult<Character>> {
      suspend { client.get(url) }
    }

  /**
   * Get the API endpoints
   *
   * primarily used for testing if api is up and running
   */
  suspend fun getApiEndPoints(): NetworkResponse<ApiEndPoints> =
    evaluateRequest<ApiEndPoints> {
      suspend { client.get("") }
    }

  /**
   * Get the characters
   *
   * Will return a paged result of characters
   * Will return the first page of characters
   */
  suspend fun getCharacters(): NetworkResponse<PagedResult<Character>> {
    return getPagedCharacterResult("/character")
  }

  /**
   * Get the characters by ids
   *
   * Will return a list of characters
   */
  suspend fun getCharactersById(ids: Array<Int>): NetworkResponse<List<Character>> {
    val arrayString = ids.joinToString(separator = ",", prefix = "[", postfix = "]")
    val urlString = "/character/${arrayString}"

    return evaluateRequest<List<Character>> {
      suspend { client.get(urlString) }
    }
  }

  /**
   * Get the characters by page url
   *
   * Will return a paged result of characters
   * Will return the specified page of characters
   */
  suspend fun getCharactersByPageUrl(url: String): NetworkResponse<PagedResult<Character>> {
    return getPagedCharacterResult(url)
  }
}