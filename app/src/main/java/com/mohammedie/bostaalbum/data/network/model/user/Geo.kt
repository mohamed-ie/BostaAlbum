package com.mohammedie.bostaalbum.data.network.model.user

import com.squareup.moshi.Json

data class Geo(

    @Json(name = "lng")
    val lng: String,

    @Json(name = "lat")
    val lat: String
)