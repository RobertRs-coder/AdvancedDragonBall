package com.advanced.advanceddragonball.domain

import com.squareup.moshi.Json

data class Hero(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "photo") val photo: String,
    @Json(name = "description") val description: String,
    @Json(name = "favorite") val favorite: Boolean
    )

