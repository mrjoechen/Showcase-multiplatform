package com.alpha.showcase.common.repo.tmdb.data

import com.alpha.showcase.common.ui.StringResources
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListResponse(
  @SerialName("page") val page: Int,
  @SerialName("results") val results: List<Movie>,
  @SerialName("total_pages") val totalPages: Int,
  @SerialName("total_results") val totalResults: Int
)
@Serializable
data class Movie(
  @SerialName("id") val id: Int,
  @SerialName("title") val title: String,
  @SerialName("overview") val overview: String,
  @SerialName("poster_path") val posterPath: String?,
  @SerialName("backdrop_path") val backdropPath: String?,
  @SerialName("vote_average") val voteAverage: Double,
  @SerialName("release_date") val releaseDate: String
)
@Serializable
data class MovieImagesResponse(
  @SerialName("id") val id: Int,
  @SerialName("backdrops") val backdrops: List<Image>,
  @SerialName("posters") val posters: List<Image>
)
@Serializable
data class Image(
  @SerialName("file_path") val filePath: String,
  @SerialName("width") val width: Int,
  @SerialName("height") val height: Int,
  @SerialName("iso_639_1") val iso6391: String?,
  @SerialName("aspect_ratio") val aspectRatio: Double,
  @SerialName("vote_average") val voteAverage: Double,
  @SerialName("vote_count") val voteCount: Int
)

@Serializable
enum class Region(val value: String, val res: String) {
  US("US", StringResources.current.tmdb_us),
  UK("UK", StringResources.current.tmdb_uk),
  CA("CA", StringResources.current.tmdb_ca),
  AU("AU", StringResources.current.tmdb_au),
  DE("DE", StringResources.current.tmdb_de),
  FR("FR", StringResources.current.tmdb_fr),
  ES("ES", StringResources.current.tmdb_es),
  IT("IT", StringResources.current.tmdb_it),
  JP("JP", StringResources.current.tmdb_jp),
  KR("KR", StringResources.current.tmdb_kr),
  CN("CN", StringResources.current.tmdb_cn),
  IN("IN", StringResources.current.tmdb_in),
  RU("RU", StringResources.current.tmdb_ru)
}
@Serializable
enum class Language(val value: String, val res: String) {
  ENGLISH_US("en-US", StringResources.current.tmdb_lang_en_us),
  GERMAN("de-DE", StringResources.current.tmdb_lang_de_de),
  FRENCH("fr-FR", StringResources.current.tmdb_lang_fr_fr),
  SPANISH("es-ES", StringResources.current.tmdb_lang_es_es),
  ITALIAN("it-IT", StringResources.current.tmdb_lang_it_it),
  JAPANESE("ja-JP", StringResources.current.tmdb_lang_ja_jp),
  KOREAN("ko-KR", StringResources.current.tmdb_lang_ko_kr),
  CHINESE("zh-CN", StringResources.current.tmdb_lang_zh_cn),
  CHINESE_TRADITIONAL("zh-TW", StringResources.current.tmdb_lang_zh_tw),
  RUSSIAN("ru-RU", StringResources.current.tmdb_lang_ru_ru)
}

enum class ImageType(val value: String, val res: String) {
  POSTER("Poster", StringResources.current.tmdb_lang_type_poster),
  BACKDROP("backdrop", StringResources.current.tmdb_image_type_backdrop)
}