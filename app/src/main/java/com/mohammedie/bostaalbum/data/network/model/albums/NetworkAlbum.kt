package com.mohammedie.bostaalbum.data.network.model.albums

import com.mohammedie.bostaalbum.data.model.Album
import com.squareup.moshi.Json

data class NetworkAlbum(

    @Json(name = "id")
    val id: Int,

    @Json(name = "title")
    val title: String,

    @Json(name = "userId")
    val userId: Int
)

fun NetworkAlbum.asAlbum() = Album(
    id = id,
    title = title
)