package com.advanced.advanceddragonball.domain

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hero (
    val id: String,
    val name: String,
    val photo: String
) : Parcelable


