package com.alpha.networkfile.rclone.entity

import kotlinx.serialization.Serializable

/**
 * Created by chenqiao on 2022/11/29.
 * e-mail : mrjctech@gmail.com
 */
@Serializable
data class About(val used: Long = -1, val free: Long = -1, val total: Long = -1, val trashed: Long = 0)