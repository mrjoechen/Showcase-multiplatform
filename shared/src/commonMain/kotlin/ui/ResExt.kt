package ui

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
  val sources: String = "Sources"
){

  companion object{
    val current = stringResourcesEn()
  }
}

fun stringResourcesEn() = StringResources(
)

fun stringResourcesZh() = StringResources(
  app_name ="图片橱窗"
)

val LocalStringResources = staticCompositionLocalOf { stringResourcesEn() }
