package com.alpha.networkfile.rclone.entity

import kotlinx.serialization.Serializable

@Serializable
data class SpaceInfo(val used: Long = -1, val free: Long = -1, val total: Long = -1, val trashed: Long = -1)