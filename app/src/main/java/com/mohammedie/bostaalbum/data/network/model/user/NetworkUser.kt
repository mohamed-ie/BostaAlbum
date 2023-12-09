package com.mohammedie.bostaalbum.data.network.model.user

import com.mohammedie.bostaalbum.data.model.User
import com.squareup.moshi.Json

data class NetworkUser(

	@Json(name="website")
	val website: String,

	@Json(name="address")
	val address: Address,

	@Json(name="phone")
	val phone: String,

	@Json(name="name")
	val name: String,

	@Json(name="company")
	val company: Company,

	@Json(name="id")
	val id: Int,

	@Json(name="email")
	val email: String,

	@Json(name="username")
	val username: String
)

fun NetworkUser.asUser() = User(
	id = id,
	name = name,
	address = address.run { "$street, $suite, $city, $zipcode" }
)

