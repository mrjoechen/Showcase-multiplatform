package com.alpha.networkfile.storage.remote

import com.alpha.networkfile.model.NetworkFile
import com.alpha.networkfile.rclone.Rclone
import kotlinx.datetime.Clock
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface RemoteApi<out T> {

  val name: String

  fun connect(): Boolean

  fun getList(params: Map<String, Any>): List<T>

}

@Serializable
@SerialName("RemoteApi")
open class RemoteApiDefaultImpl(
  override val name: String,
  val apiType: String,
  val params: Map<String, String>,
  val addTime: Long = Clock.System.now().toEpochMilliseconds()
): RemoteApi<Any> {
  override fun connect(): Boolean {
    return false
  }

  override fun getList(params: Map<String, Any>): List<Any> {
    return emptyList()
  }
}


interface RcloneRemoteApi: RemoteApi<NetworkFile> {

  val path: String

  fun connect(rclone: Rclone): Boolean {
    return false
  }

  fun genRcloneOption(): List<String>

  fun genRcloneConfig(): Map<String, String>
}

interface OAuthRcloneApi : RcloneRemoteApi {
    var token: String
}
