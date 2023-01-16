package com.advanced.advanceddragonball.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
data class Hero(
    val id: String,
    val name: String,
    val photo: String,
    val description: String,
    val favorite: Boolean,
    var locations: @RawValue List<Location>? = null
): Parcelable
