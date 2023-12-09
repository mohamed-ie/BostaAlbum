package com.mohammedie.bostaalbum.data.network.model.user

import com.squareup.moshi.Json

data class Company(

	@Json(name="bs")
	val bs: String,

	@Json(name="catchPhrase")
	val catchPhrase: String,

	@Json(name="name")
	val name: String
)