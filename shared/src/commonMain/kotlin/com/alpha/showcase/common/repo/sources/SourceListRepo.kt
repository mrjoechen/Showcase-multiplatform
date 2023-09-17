package com.alpha.showcase.common.repo.sources

import com.alpha.networkfile.storage.StorageSources
import com.alpha.networkfile.storage.remote.RemoteApi
import com.alpha.networkfile.util.StorageSourceSerializer
import com.alpha.showcase.common.BuildKonfig
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import showcaseConfigPath
import java.util.UUID


class SourceListRepo() {
    val store: KStore<StorageSources> = storeOf(filePath = showcaseConfigPath() + "/sources.json", json = StorageSourceSerializer.sourceJson)

    val defaultValue by lazy {
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

    suspend fun removeSource(source: RemoteApi<Any>) {
        val sources = getSources()
        sources.sources.remove(source)
        setSources(sources)
    }

}
