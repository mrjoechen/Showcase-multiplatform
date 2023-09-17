package com.alpha.networkfile.storage

import com.alpha.networkfile.storage.remote.RemoteApi
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * Created by chenqiao on 2022/11/30.
 * e-mail : mrjctech@gmail.com
 */
@Serializable
data class StorageSources(
  val version: Int,
  val versionName: String,
  val id: String,
  val sourceName: String,
  val timeStamp: Long,
  val sources: MutableList<RemoteApi<@Contextual Any>>
) {
  override fun equals(other: Any?): Boolean {
    return super.equals(other) && sources === (other as StorageSources).sources
  }

  override fun hashCode(): Int {
    var result = version
    result = 31 * result + versionName.hashCode()
    result = 31 * result + id.hashCode()
    result = 31 * result + sourceName.hashCode()
    result = 31 * result + timeStamp.hashCode()
    result = 31 * result + sources.hashCode()
    return result
  }
}


fun StorageSources.add(remoteApi: RemoteApi<Any>): StorageSources{
  sources.add(remoteApi)
  return this
}