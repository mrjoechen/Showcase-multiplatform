package com.alpha

import com.alpha.showcase.common.BuildKonfig
import org.lsposed.lsparanoid.Obfuscate


@Obfuscate
open class X {
    val TMDB_API_TOKEN = BuildKonfig.TMDB_TOKEN

    val APP_CENTER_SECRET_DEBUG = ""
    val APP_CENTER_SECRET_RELEASE = ""
    val APP_CENTER_SECRET_ALPHA = ""
    val APP_CENTER_SECRET_BETA = ""

    val GCP_CLIENT_ID = ""
    val GCP_SECRET = ""

    val ONEDRIVE_APP_KEY = ""

    //    val ONEDRIVE_APP_SECRET = "41U8Q~-QiARkxP~gz2KdlMeRsoKMJo3jDsiNockM"
    val ONEDRIVE_APP_SECRET = ""

    val DROPBOX_APP_KEY = ""
    val DROPBOX_APP_SECRET = ""

    companion object : X()
}