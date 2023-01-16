package com.advanced.advanceddragonball.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location (
    val id: String,
    val longitud: String,
    val latitud: String,
    val dateShow: String
): Parcelable