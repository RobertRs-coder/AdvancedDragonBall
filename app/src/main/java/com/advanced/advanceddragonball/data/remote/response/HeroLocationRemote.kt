package com.advanced.advanceddragonball.data.remote.response

import com.squareup.moshi.Json

data class HeroLocationRemote (
    @Json(name = "id") val id: String,
    @Json(name = "longitud") val longitude: String,
    @Json(name = "latitud") val latitude: String,
    @Json(name = "dateShow") val dateShow: String
)