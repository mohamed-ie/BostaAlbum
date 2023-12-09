package com.mohammedie.bostaalbum.data.network.model.user

import com.squareup.moshi.Json

data class Address(

    @Json(name = "zipcode")
    val zipcode: String,

    @Json(name = "geo")
    val geo: Geo,

    @Json(name = "suite")
    val suite: String,

    @Json(name = "city")
    val city: String,

    @Json(name = "street")
    val street: String
)