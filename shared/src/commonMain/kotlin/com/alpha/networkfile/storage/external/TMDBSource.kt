package com.alpha.networkfile.storage.external

import com.alpha.networkfile.storage.remote.RemoteApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("TMDB")
open class TMDBSource(
    override val name: String,
    val contentType: String?,
    val language: String?,
    val region: String?,
    val imageType: String?
) : RemoteApi<String> {
    override fun connect(): Boolean {
        return false
    }

    override fun getList(params: Map<String, Any>): List<String> {
        return emptyList()
    }
}