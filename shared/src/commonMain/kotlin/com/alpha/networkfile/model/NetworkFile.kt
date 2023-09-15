package com.alpha.networkfile.model

import com.alpha.networkfile.rclone.entity.Remote
import kotlinx.serialization.Serializable

@Serializable
data class NetworkFile(
    var remote: Remote,
    val path: String,
    val fileName: String,
    val isDirectory: Boolean,
    val size: Long,
    val mimeType: String,
    val modTime: String,
    val isBucket: Boolean = false
)