package com.alpha.showcase.common.repo

import com.alpha.networkfile.storage.external.GitHubSource
import com.alpha.networkfile.storage.external.TMDBSource
import com.alpha.networkfile.storage.remote.RcloneRemoteApi
import com.alpha.networkfile.storage.remote.RemoteApi
import com.alpha.showcase.common.repo.github.GithubFileRepo
import com.alpha.showcase.common.repo.tmdb.TmdbSourceRepo


class RepoManager: SourceRepository<RemoteApi<Any>, Any> {

    private val rSourceRepo by lazy {
        RSourceRepo()
    }

    private val githubFileRepo by lazy {
        GithubFileRepo()
    }

    private val tmdbSourceRepo by lazy {
        TmdbSourceRepo()
    }

    override suspend fun getItem(remoteApi: RemoteApi<Any>): Result<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun getItems(
        remoteApi: RemoteApi<Any>,
        recursive: Boolean,
        filter: ((Any) -> Boolean)?
    ): Result<List<Any>> {

        return when(remoteApi){
            is RcloneRemoteApi -> {
                rSourceRepo.getItems(remoteApi, recursive, filter)
            }

            is GitHubSource -> {
                githubFileRepo.getItems(remoteApi, recursive, filter)
            }

            is TMDBSource -> {
                tmdbSourceRepo.getItems(remoteApi, recursive)
            }

            else -> {
                Result.failure(Exception("Unsupported source!"))
            }
        }
}


}