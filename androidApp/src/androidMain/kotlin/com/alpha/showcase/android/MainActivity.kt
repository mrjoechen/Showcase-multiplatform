package com.alpha.showcase.android

import MainView
import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        WindowCompat.setDecorFitsSystemWindows(window, false)
        if (resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUI()
            // Edge to edge
            ViewCompat.setOnApplyWindowInsetsListener(
                window.decorView
            ) { _: View?, insets: WindowInsetsCompat ->
                val inset: Insets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                findViewById<View>(android.R.id.content).setPadding(inset.left, 0, inset.right, 0)
                WindowInsetsCompat.CONSUMED
            }
        } else {
            ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
                v.setPadding(0, 0, 0, 0)
                insets
            }
        }

        setContent {
            MainView()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUI()
        }

    }
}