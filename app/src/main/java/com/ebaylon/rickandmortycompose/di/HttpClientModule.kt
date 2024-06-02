package com.ebaylon.rickandmortycompose.di

import com.ebaylon.rickandmortycompose.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * Created by: ebaylon.
 * Created on: 5/30/24.
 */

@Module
@InstallIn(SingletonComponent::class)
class HttpClientModule {
  @Retention(AnnotationRetention.RUNTIME)
  @Qualifier
  annotation class RickAndMortyClient

  @RickAndMortyClient
  @Provides
  @Singleton
  fun provideRickAndMortyClient(): HttpClient {
    return HttpClient(OkHttp) {
      // json serialization
      install(ContentNegotiation) {
        json(
          Json {
            ignoreUnknownKeys = true
            isLenient = true
          }
        )
      }

      // add logger for debug
      if (BuildConfig.DEBUG) {
        install(Logging) {
          logger = object : Logger {
            override fun log(message: String) {
              println("Ktor Log: $message")
            }
          }

          level = LogLevel.INFO
        }
      }

      // timeout
      install(HttpTimeout) {
        requestTimeoutMillis = 15000L
        connectTimeoutMillis = 15000L
        socketTimeoutMillis = 15000L
      }

      // request default parameters
      val baseUrl = "rickandmortyapi.com/api"
      defaultRequest {
        host = baseUrl
        url { protocol = URLProtocol.HTTPS }
        contentType(ContentType.Application.Json)
        accept(ContentType.Application.Json)
      }
    }
  }
}