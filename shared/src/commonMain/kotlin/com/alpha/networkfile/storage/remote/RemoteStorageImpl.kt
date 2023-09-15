package com.alpha.networkfile.storage.remote

import com.alpha.networkfile.model.NetworkFile
import kotlinx.datetime.Clock
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import randomUUID

@Serializable
@SerialName("UNKNOWN")
class RemoteStorageImpl(
  override val id: String = randomUUID().toString(),
  override val host: String,
  override val port: Int,
  override val user: String,
  override val passwd: String,
  override val name: String,
  override val path: String = "/",
  override val isCrypt: Boolean = false,
  override val description: String = "",
  override val addTime: Long = Clock.System.now().toEpochMilliseconds(),
  override val lock: String = ""
): RemoteStorage() {

  override fun connect(): Boolean {

    return false
  }

  override fun getList(params: Map<String, Any>): List<NetworkFile> {
    TODO("Not yet implemented")
  }

  override fun genRcloneOption(): List<String> {
    TODO("Not yet implemented")
  }

  override fun genRcloneConfig(): Map<String, String> {
    TODO("Not yet implemented")
  }

  //  override fun getDefaultName(): String = customName ?: "${System.currentTimeMillis()}"

//  fun port(): Int?{
//    return port ?: when(networkType) {
//      is RemoteStorageNetworkFS -> networkType.defaultPort
//
//      else -> {
//        null
//      }
//    }
//
//  }
}




