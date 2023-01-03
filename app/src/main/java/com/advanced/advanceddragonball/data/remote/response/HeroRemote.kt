package com.advanced.advanceddragonball.data.remote.response

import com.squareup.moshi.Json

//DTO - Data Transfer Object
data class HeroRemote(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "photo") val photo: String,
    @Json(name = "description") val description: String,
    @Json(name = "favorite") val favorite: Boolean
)