package com.alpha.networkfile.storage.drive

import com.alpha.networkfile.model.NetworkFile
import com.alpha.networkfile.storage.remote.OAuthRcloneApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@SerialName("GooglePhotos")
open class GooglePhotos(
    override val name: String,
    override var token: String = "",
    @Transient val cid: String = "",
    @Transient val sid: String = "",
    override val path: String = ""
) : OAuthRcloneApi {
    override fun connect(): Boolean {
        return false
    }

    override fun genRcloneOption(): List<String> {
        val options = ArrayList<String>()
        options.add(name)
        options.add("google photos")
        options.add("client_id")
        options.add(cid)
        options.add("client_secret")
        options.add(sid)
        options.add("read_only")
        options.add("true")
        return options
    }

    override fun genRcloneConfig(): Map<String, String> {
        val config = mutableMapOf<String, String>()
        config["type"] = "google photos"
        config["client_id"] = cid
        config["client_secret"] = sid
        config["token"] = token
        config["read_only"] = "true"
        return config
    }

    override fun getList(params: Map<String, Any>): List<NetworkFile> {
        TODO("Not yet implemented")
    }
}