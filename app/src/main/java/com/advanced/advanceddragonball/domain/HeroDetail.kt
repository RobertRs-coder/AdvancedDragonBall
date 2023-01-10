package com.advanced.advanceddragonball.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class HeroDetail@Parcelize
data class Hero (
    val id: String,
    val name: String,
    val photo: String,
) : Parcelable {
}