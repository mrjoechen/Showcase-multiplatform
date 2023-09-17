package com.alpha.showcase.common.repo.tmdb.api

import com.alpha.X
import com.alpha.showcase.common.repo.tmdb.data.Language
import com.alpha.showcase.common.repo.tmdb.data.MovieImagesResponse
import com.alpha.showcase.common.repo.tmdb.data.MovieListResponse
import com.alpha.showcase.common.repo.tmdb.data.Region
import com.alpha.showcase.common.utils.HttpClientUtils
import com.alpha.showcase.common.utils.get

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.parameter
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json

const val TMDB_BASE_URL = "api.themoviedb.org"
const val TMDB_BASE_URL_PROXY = "api.tmdb.org"

//https://image.tmdb.org/t/p/original/dKtgSYi2AwlaHYo1Iqie8wlMsc5.jpg
const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original/"
//https://www.themoviedb.org/t/p/w1280/7YN6LU2sGJepnZKOa2NW2YVjq1S.jpg
//const val TMDB_IMAGE_BASE_URL = "https://www.themoviedb.org/t/p/w1280/"


val tmdbHttpClient = HttpClient {
  install(ContentNegotiation) {
    json(HttpClientUtils.json)
  }

  install(Logging) { logger = Logger.SIMPLE }

  defaultRequest {
    url {
      host = TMDB_BASE_URL_PROXY
      protocol = URLProtocol.HTTPS
      headers.append("Authorization", "Bearer ${X.TMDB_API_TOKEN}")
    }
  }
}

val tmdbHttpClientFallback = HttpClient {
  install(ContentNegotiation) {
    json(HttpClientUtils.json)
  }

  install(Logging) { logger = Logger.SIMPLE }

  defaultRequest {
    url {
      host = TMDB_BASE_URL
      protocol = URLProtocol.HTTPS
      headers.append("Authorization", "Bearer ${X.TMDB_API_TOKEN}")
    }
  }
}

class TmdbService(private val httpClient: HttpClient = tmdbHttpClient) {
  suspend fun getTopRatedMovies(
    page: Int = 1,
    region: String = Region.US.value,
    language: String = Language.ENGLISH_US.value
  ): Result<MovieListResponse> = httpClient.get {
    url {
      path("3/movie/top_rated")
      parameter("page", page)
      parameter("region", region)
      parameter("language", language)
    }
  }

  suspend fun getPopularMovies(
    page: Int = 1,
    region: String = Region.US.value,
    language: String = Language.ENGLISH_US.value
  ): Result<MovieListResponse> = httpClient.get {
    url {
      path("3/movie/popular")
      parameter("page", page)
      parameter("region", region)
      parameter("language", language)
    }
  }

  suspend fun getUpcomingMovies(
    page: Int = 1,
    region: String = Region.US.value,
    language: String = Language.ENGLISH_US.value
  ): Result<MovieListResponse> = httpClient.get {
    url {
      path("3/movie/upcoming")
      parameter("page", page)
      parameter("region", region)
      parameter("language", language)
    }
  }

  suspend fun getNowPlayingMovies(
    page: Int = 1,
    region: String = Region.US.value,
    language: String = Language.ENGLISH_US.value
  ): Result<MovieListResponse> = httpClient.get {
    url {
      path("3/movie/now_playing")
      parameter("page", page)
      parameter("region", region)
      parameter("language", language)
    }
  }

  suspend fun getMovieImages(
    movieId: Int
  ): Result<MovieImagesResponse> = httpClient.get {
    url {
      path("3/movie/$movieId/images")
    }
  }

}