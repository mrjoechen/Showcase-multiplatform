package com.alpha.showcase.common.ui.settings

import com.alpha.showcase.common.data.Settings
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import showcaseConfigPath

class SettingPreferenceRepo {

  private val store: KStore<Settings> = storeOf(filePath = showcaseConfigPath() + "/settings")

  suspend fun getSettings(): Settings {
    return store.get() ?: Settings.getDefaultInstance()
  }

  suspend fun updateSettings(update: (Settings) -> Settings): Settings {
    val value = update(getSettings())
    store.set(value)
    return value
  }


}
