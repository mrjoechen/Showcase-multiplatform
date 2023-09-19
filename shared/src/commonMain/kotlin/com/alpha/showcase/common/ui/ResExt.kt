package com.alpha.showcase.common.ui

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

//val LocalStringResources = staticCompositionLocalOf { ResourcesImp("EN") }
//
//
//@Immutable
//class ResourcesImp(
//  private val locale: String
//) {
//  fun getString(): StringResources {
//    val language = when(locale.lowercase()) {
//      "zh" -> ZH
//      else -> EN
//    }
//    return language
//  }
//}
//
//@Immutable
//private object EN: StringResources()
//
//@Immutable
//private object ZH: StringResources() {
//  override val app_name: String = "图片橱窗"
//}
//
//abstract class StringResources {
//  open val app_name: String = "ShowCase"
//  open val settings: String = "Setting"
//}


@Immutable
data class StringResources(
    val app_name: String = "ShowCase",
    val settings: String = "Settings",
    val sources: String = "Sources",
    val preview: String = "Preview",

    val tmdb_top_rated: String = "Top Rated",
    val tmdb_popular: String = "Popular",
    val tmdb_upcoming: String = "Upcoming",
    val tmdb_now_playing: String = "Now Playing",
    val tmdb_us: String = "United States",
    val tmdb_uk: String = "United Kingdom",
    val tmdb_ca: String = "Canada",
    val tmdb_au: String = "Australia",
    val tmdb_de: String = "Germany",
    val tmdb_fr: String = "France",
    val tmdb_es: String = "Spain",
    val tmdb_it: String = "Italy",
    val tmdb_jp: String = "Japan",
    val tmdb_kr: String = "Korea",
    val tmdb_cn: String = "China",
    val tmdb_in: String = "India",
    val tmdb_ru: String = "Russia",
    val tmdb_lang_en_us: String = "English",
    val tmdb_lang_de_de: String = "German",
    val tmdb_lang_fr_fr: String = "French",
    val tmdb_lang_es_es: String = "Spanish",
    val tmdb_lang_it_it: String = "Italian",
    val tmdb_lang_ja_jp: String = "Japanese",
    val tmdb_lang_ko_kr: String = "Korean",
    val tmdb_lang_zh_cn: String = "Simplified Chinese",
    val tmdb_lang_zh_tw: String = "Traditional Chinese",
    val tmdb_lang_ru_ru: String = "Russian",
    val tmdb_image_type_backdrop: String = "Backdrop",
    val tmdb_lang_type_poster: String = "Poster",

    val data_not_found: String = "Data Not Found",
    val data_under_construct: String = "Under Construction"
) {

    companion object {
        val current = stringResourcesEn()
    }
}

fun stringResourcesEn() = StringResources(
)

fun stringResourcesZh() = StringResources(
    app_name = "图片橱窗"
)

val LocalStringResources = staticCompositionLocalOf { stringResourcesEn() }
