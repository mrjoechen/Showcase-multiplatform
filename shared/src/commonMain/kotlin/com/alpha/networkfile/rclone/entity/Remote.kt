package com.alpha.networkfile.rclone.entity

import com.alpha.networkfile.storage.StorageType
import kotlinx.serialization.Serializable

/**
 * Created by chenqiao on 2022/11/29.
 * e-mail : mrjctech@gmail.com
 */
@Serializable
data class Remote(val key: String, val remoteConfig: RemoteConfig)

fun Remote.isType(storageType: StorageType) = remoteConfig.type.uppercase() == storageType.typeName