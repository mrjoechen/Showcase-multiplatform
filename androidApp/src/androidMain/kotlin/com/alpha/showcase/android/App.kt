package com.alpha.showcase.android

import android.app.Application
import androidApp

class App: Application() {
  override fun onCreate() {
    super.onCreate()
    androidApp = this
  }
}