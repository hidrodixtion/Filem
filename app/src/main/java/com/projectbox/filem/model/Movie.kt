package com.projectbox.filem.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by adinugroho
 */
@Parcelize
data class Movie(
    val id: String,
    @SerializedName("vote_average") val vote: Double,
    @SerializedName("vote_count") val voteCount: Double,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val poster: String,
    @SerializedName("release_date") val releaseDate: String
) : Parcelable

data class MovieResponse(
    val results: List<Movie>
)