package com.alpha.showcase.common.repo.tmdb

import com.alpha.networkfile.storage.external.TMDBSource
import com.alpha.showcase.common.repo.SourceRepository
import com.alpha.showcase.common.repo.tmdb.api.TMDB_IMAGE_BASE_URL
import com.alpha.showcase.common.repo.tmdb.api.TmdbService
import com.alpha.showcase.common.repo.tmdb.api.tmdbHttpClientFallback
import com.alpha.showcase.common.repo.tmdb.data.ImageType
import com.alpha.showcase.common.repo.tmdb.data.Language
import com.alpha.showcase.common.repo.tmdb.data.Movie
import com.alpha.showcase.common.repo.tmdb.data.Region
import com.alpha.showcase.common.ui.StringResources


const val TOP_RATED_MOVIES = "Top Rated"
const val POPULAR_MOVIES = "Popular"
const val UPCOMING_MOVIES = "Upcoming"
const val NOW_PLAYING_MOVIES = "Now Playing"


sealed class TMDBSourceType(val type: String, val title: String) {
    object TopRated : TMDBSourceType(TOP_RATED_MOVIES, StringResources.current.tmdb_top_rated)
    object Popular : TMDBSourceType(POPULAR_MOVIES, StringResources.current.tmdb_popular)
    object Upcoming : TMDBSourceType(UPCOMING_MOVIES, StringResources.current.tmdb_upcoming)
    object NowPlaying : TMDBSourceType(NOW_PLAYING_MOVIES, StringResources.current.tmdb_now_playing)
}

class TmdbSourceRepo : SourceRepository<TMDBSource, String> {
    private var tmdbService = TmdbService()
    override suspend fun getItem(remoteApi: TMDBSource): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getItems(
        remoteApi: TMDBSource,
        recursive: Boolean,
        filter: ((String) -> Boolean)?
    ): Result<List<String>> {

        val result = request(remoteApi, tmdbService)
        return if (result.isSuccess) result else {
            tmdbService = TmdbService(tmdbHttpClientFallback)
            request(remoteApi, tmdbService)
        }

    }

    private suspend fun request(
        remoteApi: TMDBSource,
        tmdbService: TmdbService
    ): Result<List<String>> {
        return try {

            val language = remoteApi.language ?: Language.ENGLISH_US.value
            val region = remoteApi.region ?: Region.US.value
            val imageType = remoteApi.imageType ?: ImageType.POSTER.value
            val content = when (remoteApi.contentType) {
                TOP_RATED_MOVIES -> tmdbService.getTopRatedMovies(
                    language = language,
                    region = region
                )

                POPULAR_MOVIES -> tmdbService.getPopularMovies(language = language, region = region)
                UPCOMING_MOVIES -> tmdbService.getUpcomingMovies(
                    language = language,
                    region = region
                )

                NOW_PLAYING_MOVIES -> tmdbService.getNowPlayingMovies(
                    language = language,
                    region = region
                )

                else -> tmdbService.getNowPlayingMovies(language = language, region = region)
            }

            if (content.isSuccess) {
                content.getOrNull()?.let { movieListResponse ->
                    if (movieListResponse.results.isEmpty()) {
                        Result.failure(Exception("No content found."))
                    } else {
                        val result = mutableListOf<Movie>()
                        result.addAll(movieListResponse.results)
                        if (movieListResponse.page < movieListResponse.totalPages) {
                            val nextPage = movieListResponse.page + 1
                            val nextContent = when (remoteApi.contentType) {
                                TOP_RATED_MOVIES -> tmdbService.getTopRatedMovies(
                                    page = nextPage,
                                    language = language,
                                    region = region
                                )

                                POPULAR_MOVIES -> tmdbService.getPopularMovies(
                                    page = nextPage,
                                    language = language,
                                    region = region
                                )

                                UPCOMING_MOVIES -> tmdbService.getUpcomingMovies(
                                    page = nextPage,
                                    language = language,
                                    region = region
                                )

                                NOW_PLAYING_MOVIES -> tmdbService.getNowPlayingMovies(
                                    page = nextPage,
                                    language = language,
                                    region = region
                                )

                                else -> tmdbService.getNowPlayingMovies(
                                    page = nextPage,
                                    language = language,
                                    region = region
                                )
                            }


                            nextContent.getOrNull()?.results?.let {
                                result.addAll(it)
                            }
                        }

                        Result.success(result.filter {
                            val imagePath =
                                if (imageType == ImageType.POSTER.value) it.posterPath else it.backdropPath
                            imagePath != null && imagePath.isNotEmpty()
                        }.map {
                            TMDB_IMAGE_BASE_URL + if (imageType == ImageType.POSTER.value) it.posterPath else it.backdropPath
                        })
                    }
                } ?: Result.failure(Exception("No content found."))
            } else {
                Result.failure(Exception("Request failed."))
            }


        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

}