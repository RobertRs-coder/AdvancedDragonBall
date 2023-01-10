package com.advanced.advanceddragonball.domain

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Detail (
    val id: String,
    val name: String,
    val photo: String,
    val description: String,
    val favorite: Boolean
) : Parcelable


