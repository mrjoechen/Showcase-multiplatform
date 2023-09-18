package com.alpha.showcase.common.utils

import com.alpha.X
import com.alpha.networkfile.storage.TYPE_DROPBOX
import com.alpha.networkfile.storage.TYPE_FTP
import com.alpha.networkfile.storage.TYPE_GOOGLE_DRIVE
import com.alpha.networkfile.storage.TYPE_GOOGLE_PHOTOS
import com.alpha.networkfile.storage.TYPE_LOCAL
import com.alpha.networkfile.storage.TYPE_ONE_DRIVE
import com.alpha.networkfile.storage.TYPE_SFTP
import com.alpha.networkfile.storage.TYPE_SMB
import com.alpha.networkfile.storage.TYPE_UNKNOWN
import com.alpha.networkfile.storage.TYPE_WEBDAV
import com.alpha.networkfile.storage.drive.DropBox
import com.alpha.networkfile.storage.drive.GoogleDrive
import com.alpha.networkfile.storage.drive.GooglePhotos
import com.alpha.networkfile.storage.drive.OneDrive
import com.alpha.networkfile.storage.external.GitHubSource
import com.alpha.networkfile.storage.external.TMDBSource
import com.alpha.networkfile.storage.external.TYPE_GITHUB
import com.alpha.networkfile.storage.external.TYPE_TMDB
import com.alpha.networkfile.storage.external.TYPE_UNSPLASH
import com.alpha.networkfile.storage.external.UnSplashSource
import com.alpha.networkfile.storage.remote.Ftp
import com.alpha.networkfile.storage.remote.Local
import com.alpha.networkfile.storage.remote.OAuthRcloneApi
import com.alpha.networkfile.storage.remote.RemoteApi
import com.alpha.networkfile.storage.remote.Sftp
import com.alpha.networkfile.storage.remote.Smb
import com.alpha.networkfile.storage.remote.WebDav


fun RemoteApi<Any>.getIcon(): String {

    return when (this) {
        is Local -> {
            "ic_local.xml"
        }

        is Smb -> {
            "ic_smb.xml"
        }

        is Ftp -> {
            "ic_ftp.xml"
        }

        is WebDav -> {
            "ic_webdav.xml"
        }

        is Sftp -> {
            "ic_terminal.xml"
        }

        is GoogleDrive -> {
            "ic_google_drive.xml"
        }

        is GitHubSource -> {
            "ic_github.xml"
        }

        is TMDBSource -> {
            "ic_tmdb.xml"
        }

        is UnSplashSource -> {
            "ic_unsplash.xml"
        }

        is GooglePhotos -> {
            "ic_google_photos.xml"
        }

        is OneDrive -> {
            "ic_onedrive.xml"
        }

        is DropBox -> {
            "ic_dropbox.xml"
        }

        else -> {
            "ic_info.xml"
        }
    }
}

fun RemoteApi<Any>.type(): Int {

    return when (this) {
        is Local -> TYPE_LOCAL
        is Smb -> TYPE_SMB
        is Ftp -> TYPE_FTP
        is WebDav -> TYPE_WEBDAV
        is Sftp -> TYPE_SFTP
        is GitHubSource -> TYPE_GITHUB
        is TMDBSource -> TYPE_TMDB
        is UnSplashSource -> TYPE_UNSPLASH
        is GoogleDrive -> TYPE_GOOGLE_DRIVE
        is GooglePhotos -> TYPE_GOOGLE_PHOTOS
        is OneDrive -> TYPE_ONE_DRIVE
        is DropBox -> TYPE_DROPBOX
        else -> TYPE_UNKNOWN
    }
}

fun OAuthRcloneApi.supplyConfig(): Map<String, String>{
    return when(this){
        is GoogleDrive -> {
            genRcloneConfig().toMutableMap().apply {
                this["client_id"] = X.GCP_CLIENT_ID
                this["client_secret"] = X.GCP_SECRET
            }
        }

        is DropBox -> {
            genRcloneConfig().toMutableMap().apply {
                this["client_id"] = X.DROPBOX_APP_KEY
                this["client_secret"] = X.DROPBOX_APP_SECRET
            }
        }

        is OneDrive -> {
            genRcloneConfig().toMutableMap().apply {
                this["client_id"] = X.ONEDRIVE_APP_KEY
                this["client_secret"] = X.ONEDRIVE_APP_SECRET
            }
        }

        is GooglePhotos -> {
            genRcloneConfig().toMutableMap().apply {
                this["client_id"] = X.GCP_CLIENT_ID
                this["client_secret"] = X.GCP_SECRET
            }
        }

        else -> {
            emptyMap()
        }
    }
}