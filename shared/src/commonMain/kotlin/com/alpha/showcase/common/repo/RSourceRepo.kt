package com.alpha.showcase.common.repo

import com.alpha.networkfile.model.NetworkFile
import com.alpha.networkfile.rclone.Rclone
import com.alpha.networkfile.rclone.succeeded
import com.alpha.networkfile.storage.remote.RcloneRemoteApi
import com.alpha.showcase.common.repo.SourceRepository

class RSourceRepo: SourceRepository<RcloneRemoteApi, NetworkFile> {
  private lateinit var rclone: Rclone

  override suspend fun getItem(remoteApi: RcloneRemoteApi): Result<NetworkFile> {
    val fileInfo = rclone.getFileInfo(remoteApi)
    return if (fileInfo.succeeded)
      Result.success((fileInfo as com.alpha.networkfile.rclone.Result.Success).data !!)
    else
      Result.failure(Exception((fileInfo as com.alpha.networkfile.rclone.Result.Error).message))
  }

  override suspend fun getItems(
    remoteApi: RcloneRemoteApi,
    recursive: Boolean,
    filter: ((NetworkFile) -> Boolean)?
  ): Result<List<NetworkFile>> {

    val fileItems = rclone.getFileItems(remoteApi, recursive, filter)
    return if (fileItems.succeeded)
      Result.success((fileItems as com.alpha.networkfile.rclone.Result.Success).data!!)
    else
      Result.failure(Exception((fileItems as com.alpha.networkfile.rclone.Result.Error).message))
  }

}