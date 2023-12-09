package com.mohammedie.bostaalbum.data.network.model.photos

import com.mohammedie.bostaalbum.data.model.Photo
import com.squareup.moshi.Json

data class NetworkPhoto(

    @Json(name = "albumId")
    val albumId: Int,

    @Json(name = "id")
    val id: Int,

    @Json(name = "title")
    val title: String,

    @Json(name = "url")
    val url: String,

    @Json(name = "thumbnailUrl")
    val thumbnailUrl: String
)

fun NetworkPhoto.asPhoto() = Photo(
    id = id,
    title = title,
    url = url,
    thumbnailUrl = thumbnailUrl
)