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
  val data_under_construct: String = "Under Construction",

  val unsupport_type: String = "Unsupport Type"

) {


  val Url: String = "Url"
  val language_of_film_content: String = "The language of the film content"
  val choose_image_type: String = "Choose Image Type"
  val show_movie_from_selected_region: String = "Show movies from selected region"
  val choose_region: String = "Choose Region"
  val choose_language: String = "Choose Language"
  val choose_type: String = "Choose Type"
  val github_acess_token_tips_5: String = "5. Click the \"Generate token\" button at the bottom of the page"
  val github_acess_token_tips_4: String = "4. Copy the generated token and paste it into the \"Github Access Token\" field"
  val github_acess_token_tips_3: String = "3. Fill in the form and click the \"Generate token\" button"
  val github_acess_token_tips_2: String = "2. Click the \"Generate new token\" button"
  val github_acess_token_tips_1: String = "1. Open the link below"
  val github_access_token_tips_title: String = "How to get github access token"
  val name_require_hint: String = "Name Require"
  val repo_url_require_hint: String = "Repo Url Require"
  val sub_folder_hint: String = "Sub Folder"
  val leave_blank_is_default_branch: String = "Leave blank is default branch"
  val branche_name: String = "Branch Name"
  val repo_name: String = "Repo Name"
  val repo_owner: String = "Repo Owner"
  val my: String = "My"
  val host: String = "Host"
  val port: String = "Port"
  val user: String = "User"
  val path: String = "Path"
  val show_password: String = "Show Password"
  val hide_password: String = "Hide Password"
  val password: String = "Password"
  val source_name: String = "Source Name"
  val ip_is_invalid: String = "ip is invalid"
  val port_is_invalid: String = "port is invalid"
  val url_is_invalid: String = "url is invalid"
  val branch_name_is_invalid: String = "branch name is invalid"
  val path_is_invalid: String = "path is invalid"
  val host_is_invalid: String = "host is invalid"
  val name_is_invalid: String = "name is invalid"
  val link_to: String = "Link to"
  val save: String = "Save"
  val error: String = "Error"
  val test_connection: String = "Test Connection"
  val connection_tiemout: String = "Connection Timeout"
  val save_success: String = "Save Success"
  val add_success: String = "Add Success"
  val source_name_already_exists: String = "Source name already exists"
  val connection_failed: String = "Connection Failed"
  val connection_successful: String = "Connection Successful"
  val edit: String = "Edit"
  val add: String = "Add"
  val source: String = "Source"

  companion object {
    val current = stringResourcesEn()
  }
}

fun stringResourcesEn() = StringResources(
)

fun stringResourcesZh() = StringResources(
  app_name = "图片橱窗"
)

val LocalStringResources = staticCompositionLocalOf {stringResourcesEn()}
