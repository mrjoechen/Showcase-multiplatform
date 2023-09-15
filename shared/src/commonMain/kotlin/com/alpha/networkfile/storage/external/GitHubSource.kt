package com.alpha.networkfile.storage.external

import com.alpha.networkfile.storage.remote.RemoteApi
import com.alpha.networkfile.storage.remote.RemoteApiDefaultImpl
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("GitHub")
open class GitHubSource(
    override val name: String,
    val repoUrl: String,
    val token: String,
    val path: String = "",
    val branchName: String? = null
) : RemoteApi<String> {
    override fun connect(): Boolean {
        return false
    }

    override fun getList(params: Map<String, Any>): List<String> {
        return emptyList()
    }
}