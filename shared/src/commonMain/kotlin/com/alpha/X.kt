package com.alpha

import com.alpha.showcase.common.BuildKonfig
import org.lsposed.lsparanoid.Obfuscate


@Obfuscate
open class X {
    val TMDB_API_TOKEN = BuildKonfig.TMDB_TOKEN

    companion object : X()
}