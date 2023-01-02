package com.advanced.advanceddragonball.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class Hero(
    val id: String,
    val name: String,
    val photo: String
)


