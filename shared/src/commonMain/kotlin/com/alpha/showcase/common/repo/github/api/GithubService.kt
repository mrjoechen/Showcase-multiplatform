package com.alpha.showcase.common.repo.github.api

import com.alpha.showcase.common.repo.github.data.GithubFile
import com.alpha.showcase.common.utils.HttpClientUtils
import com.alpha.showcase.common.utils.get
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


const val GITHUB_BASE_URL = "api.github.com"
const val OAUTH_TYPE = 0
const val TOKEN_TYPE = 1


val GithubHttpClient = HttpClient {
  install(ContentNegotiation) {
    json(HttpClientUtils.json)
  }

  install(Logging) { logger = Logger.SIMPLE }

  defaultRequest {
    url {
      host = GITHUB_BASE_URL
      protocol = URLProtocol.HTTPS
    }
  }
}

class GithubService(private val client: HttpClient = GithubHttpClient) {

  suspend fun getContents(
    owner: String,
    repo: String
  ): Result<List<GithubFile>> = client.get {
    url {
      path("repos/$owner/$repo/contents")
    }
  }

  suspend fun getContents(
    owner: String,
    repo: String,
    path: String
  ): Result<List<GithubFile>> = client.get {
    url {
      path("repos/$owner/$repo/contents/$path")
    }
  }

  suspend fun getContents(
    owner: String,
    repo: String,
    path: String,
    branch: String?
  ): Result<List<GithubFile>> = client.get {
    url {
      path("repos/$owner/$repo/contents/$path")
      branch?.let { parameters.append("ref", it) }
    }
  }

  suspend fun getContentsWithAuth(
    owner: String,
    repo: String,
    authorization: String
  ): Result<List<GithubFile>> = client.get {
    url {
      path("repos/$owner/$repo/contents")
    }
    header("Authorization", authorization)
  }

  suspend fun getContentsWithAuth(
    owner: String,
    repo: String,
    path: String,
    branch: String?,
    authorization: String
  ): Result<List<GithubFile>> = client.get {
    url {
      path("repos/$owner/$repo/contents/$path")
      branch?.let { parameters.append("ref", it) }
    }
    header("Authorization", authorization)
  }

}
