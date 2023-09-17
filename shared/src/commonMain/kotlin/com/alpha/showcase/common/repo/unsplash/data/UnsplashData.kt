package com.alpha.showcase.common.repo.unsplash.data

import kotlinx.serialization.Serializable

/**
 * Created by chenqiao on 2023/3/31.
 * e-mail : mrjctech@gmail.com
 */

@Serializable
data class Photo(
  val id: String,
  val width: Int,
  val height: Int,
  val description: String,
  val urls: Map<String, String> // 包含原始、大、中等和缩略图URL的集合
)
@Serializable
data class Collection(
  val id: String,
  val title: String,
  val description: String?,
  val published_at: String?,
  val updated_at: String?,
  val curated: Boolean?,
  val featured: Boolean?,
  val total_photos: Int?,
  val private: Boolean?,
  val share_key: String?,
  val cover_photo: Photo?,
  val preview_photos: List<PhotoPreview>?,
  val links: CollectionLinks?
)
@Serializable
data class CollectionLinks(
  val self: String?,
  val html: String?,
  val photos: String?,
  val related: String?
)
@Serializable
data class PhotoPreview(
  val id: String?,
  val urls: PhotoUrls?
)
@Serializable
data class PhotoUrls(
  val raw: String?,
  val full: String?,
  val regular: String?,
  val small: String?,
  val thumb: String?
)

