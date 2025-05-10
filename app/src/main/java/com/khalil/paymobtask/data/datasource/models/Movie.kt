package com.khalil.paymobtask.data.datasource.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorites")
data class Movie(
    @PrimaryKey val id: Int,
    val title: String,
    val poster_path: String? = null,
    val release_date: String? = null,
    val vote_average: Double? = null,
    val overview: String? = null,
    val original_language: String? = null,
    val backdrop_path: String? = null,
    val popularity: Double? = null,
    val adult: Boolean? = null,
    var isFavorite: Boolean = false
) : Parcelable