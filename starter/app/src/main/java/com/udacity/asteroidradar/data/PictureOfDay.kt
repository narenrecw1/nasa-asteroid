package com.udacity.asteroidradar.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
@Entity(tableName = "picture_of_the_day")
data class PictureOfDay(
    @PrimaryKey
    val id:Int = 0,
    @Json(name = "media_type") val mediaType: String,
    val title: String,
    val url: String)