package com.alpha.showcase.common.repo.unsplash.api

import com.alpha.showcase.common.repo.unsplash.data.Photo
import com.alpha.showcase.common.repo.unsplash.data.Collection
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
import kotlinx.serialization.json.Json


const val UNSPLASH_BASE_URL = "api.unsplash.com"

val unsplashHttpClient = HttpClient {
    install(ContentNegotiation) {
        json(HttpClientUtils.json)
    }

    install(Logging) { logger = Logger.SIMPLE }

    defaultRequest {
        url {
            host = UNSPLASH_BASE_URL
            protocol = URLProtocol.HTTPS
            headers.append("Authorization", "Client-ID <YOUR_ACCESS_KEY>")
        }
    }
}

class UnsplashService {
    suspend fun getPhotos(
        page: Int,
        perPage: Int
    ): Result<List<Photo>> = unsplashHttpClient.get {
        url {
            path("photos")
            parameter("page", page)
            parameter("per_page", perPage)
        }
    }

    suspend fun getCollections(
        username: String,
        page: Int,
        perPage: Int
    ): Result<List<Collection>> = unsplashHttpClient.get {
        url {
            path("users", username, "collections")
            parameter("page", page)
            parameter("per_page", perPage)
        }

    }

}
