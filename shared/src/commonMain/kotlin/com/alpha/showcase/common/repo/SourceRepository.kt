package com.alpha.showcase.common.repo

import com.alpha.networkfile.storage.remote.RemoteApi

interface SourceRepository<T: RemoteApi<R>, out R> {
  suspend fun getItem(remoteApi: T): Result<R>
  suspend fun getItems(remoteApi: T, recursive: Boolean = false, filter: ((R) -> Boolean)? = null): Result<List<R>>
}