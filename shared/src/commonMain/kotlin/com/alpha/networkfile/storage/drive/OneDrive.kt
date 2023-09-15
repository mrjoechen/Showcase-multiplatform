package com.alpha.networkfile.storage.drive

import com.alpha.networkfile.model.NetworkFile
import com.alpha.networkfile.storage.remote.OAuthRcloneApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@SerialName("OneDrive")
open class OneDrive(
    override val name: String,
    override var token: String = "",
    @Transient val cid: String = "",
    @Transient val sid: String = "",
    val driveId: String = "",
    val driveType: String = "personal",
    override val path: String = ""
) : OAuthRcloneApi {
    override fun connect(): Boolean {
        return false
    }

    override fun genRcloneOption(): List<String> {
        val options = ArrayList<String>()
        options.add(name)
        options.add("onedrive")
        options.add("client_id")
        options.add(cid)
        options.add("client_secret")
        options.add(sid)
        return options
    }

    override fun genRcloneConfig(): Map<String, String> {
        val config = mutableMapOf<String, String>()
        config["type"] = "onedrive"
        config["client_id"] = cid
        config["client_secret"] = sid
        config["token"] = token
        config["drive_id"] = driveId
        config["drive_type"] = driveType
        return config
    }

    override fun getList(params: Map<String, Any>): List<NetworkFile> {
        TODO("Not yet implemented")
    }
}