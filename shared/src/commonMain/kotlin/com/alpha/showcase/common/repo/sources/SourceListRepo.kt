package com.alpha.showcase.common.repo.sources

import com.alpha.networkfile.rclone.Result
import com.alpha.networkfile.storage.StorageSources
import com.alpha.networkfile.storage.remote.RcloneRemoteApi
import com.alpha.networkfile.storage.remote.RemoteApi
import com.alpha.networkfile.util.StorageSourceSerializer
import com.alpha.showcase.common.BuildKonfig
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import showcaseConfigPath
import java.util.UUID


class SourceListRepo() {
  private val store: KStore<StorageSources> = storeOf(filePath = showcaseConfigPath() + "/sources.json", json = StorageSourceSerializer.sourceJson)

  private val defaultValue by lazy {
    StorageSources(
      BuildKonfig.versionCode,
      BuildKonfig.versionName,
      UUID.randomUUID().toString(),
      "default",
      System.currentTimeMillis(),
      mutableListOf()
    )
  }

  suspend fun getSources(): StorageSources {
    return store.get() ?: defaultValue
  }

  suspend fun setSources(sources: StorageSources) {
    store.set(sources)
  }

  suspend fun addSource(source: RemoteApi<Any>) {
    val sources = getSources()
    sources.sources.add(source)
    setSources(sources)
  }


  fun setUpSourcesAndConfig(remoteApi: RemoteApi<Any>) {
    TODO("Not yet implemented")
  }

  suspend fun saveSource(remoteApi: RemoteApi<Any>): Boolean {
    val storageSources = getSources()
    storageSources.sources.add(remoteApi)
    setSources(storageSources)
    return true
  }

  suspend fun deleteSource(remoteApi: RemoteApi<Any>): Boolean {
    val oldSources = getSources()

    val sources = mutableListOf<RemoteApi<Any>>()
    sources.addAll(oldSources.sources)
    val remoteStorages = oldSources.sources.filter {ele ->
      ele.name == remoteApi.name
    }
    remoteStorages.forEach {ele ->
      sources.remove(ele)
    }
    val storageSources = StorageSources(
      oldSources.version,
      oldSources.versionName,
      oldSources.id,
      oldSources.sourceName,
      oldSources.timeStamp,
      sources
    )
    setSources(storageSources)
    return true
  }

  fun getSourceFileDirItems(remoteApi: RcloneRemoteApi, path: String): Result<Any> {
    TODO("Not yet implemented")
  }

  fun <T> linkConnection(
    oAuthRcloneApi: T, onRetrieveOauthUrl: (String?) -> Unit
  ): T? {
    TODO("Not yet implemented")
  }

  fun checkConnection(remoteApi: RemoteApi<Any>): Result<Any> {
    TODO("Not yet implemented")
  }

}
