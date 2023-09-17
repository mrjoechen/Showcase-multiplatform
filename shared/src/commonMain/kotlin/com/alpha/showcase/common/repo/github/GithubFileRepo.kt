package com.alpha.showcase.common.repo.github

import com.alpha.networkfile.storage.external.GitHubSource
import com.alpha.showcase.common.repo.SourceRepository
import com.alpha.showcase.common.repo.github.api.GithubService
import com.alpha.showcase.common.repo.github.data.FILE_TYPE_DIR
import com.alpha.showcase.common.repo.github.data.GithubFile

class GithubFileRepo : SourceRepository<GitHubSource, String> {

    private val githubService = GithubService()

    private val proxy_prefix = "https://ghproxy.com/"

    override suspend fun getItem(remoteApi: GitHubSource): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getItems(
        remoteApi: GitHubSource,
        recursive: Boolean,
        filter: ((String) -> Boolean)?
    ): Result<List<String>> {

        return try {
            remoteApi.getOwnerAndRepo()?.run {
                val contents = if (remoteApi.token.isBlank()) githubService.getContents(
                    first,
                    second,
                    remoteApi.path,
                    if (remoteApi.branchName.isNullOrBlank()) null else remoteApi.branchName
                ) else githubService.getContentsWithAuth(
                    first,
                    second,
                    remoteApi.path,
                    if (remoteApi.branchName.isNullOrBlank()) null else remoteApi.branchName,
                    "token ${remoteApi.token}"
                )

                if (contents.isSuccess) {

                    if (recursive) {
                        val recursiveContent = mutableListOf<GithubFile>()
                        contents.getOrNull()?.forEach {
                            if (it.type == FILE_TYPE_DIR) {
                                val subFiles = traverseDirectory(
                                    first,
                                    second,
                                    it.path,
                                    if (remoteApi.branchName.isNullOrBlank()) null else remoteApi.branchName,
                                    "token ${remoteApi.token}"
                                )
                                recursiveContent.addAll(subFiles)
                            } else {
                                recursiveContent.add(it)
                            }
                        }
                        Result.success(recursiveContent.run {
                            map {
                                it.download_url ?: ""
                            }.filter {
                                filter?.invoke(it) ?: true
                            }
                        })
                    } else {
                        Result.success(contents.getOrNull()?.run {
                            map {
                                it.download_url ?: ""
                            }.filter {
                                filter?.invoke(it) ?: true
                            }
                        }?: emptyList())
                    }
                } else {
                    Result.failure(Exception("Contents is empty!"))
                }

            } ?: Result.failure(Exception("Url error!"))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }

    }


    private suspend fun traverseDirectory(
        user: String,
        repo: String,
        path: String,
        branch: String?,
        token: String
    ): List<GithubFile> {

        val result = mutableListOf<GithubFile>()

        val files = githubService.getContentsWithAuth(
            user,
            repo,
            path,
            if (branch.isNullOrBlank()) null else branch,
            token
        )

        files.getOrNull()?.forEach {file ->
            if (file.type == FILE_TYPE_DIR) {
                result.addAll(traverseDirectory(user, repo, file.path, branch, token))
            } else {
                result.add(file)
            }
        }


        return result
    }

}

fun GitHubSource.getOwnerAndRepo(): Pair<String, String>? {
    val regex = "https://github.com/(.*)/(.*)".toRegex()
    val matchResult = regex.find(repoUrl)

    if (matchResult != null) {
        val owner = matchResult.groupValues[1]
        val repo = matchResult.groupValues[2]
        return owner to repo
    }
    return null
}


