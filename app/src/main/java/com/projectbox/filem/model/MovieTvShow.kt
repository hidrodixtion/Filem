package com.projectbox.filem.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by adinugroho
 */
@Parcelize
data class MovieTvShow(
    val id: String,
    @SerializedName("vote_average") val vote: Double,
    @SerializedName("vote_count") val voteCount: Double,
    @SerializedName("title") val movieTitle: String?,
    @SerializedName("name") val showTitle: String?,
    val overview: String,
    @SerializedName("poster_path") val poster: String,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("release_date") val releaseDate: String?
) : Parcelable

data class MovieTvShowResponse(
    val results: List<MovieTvShow>
)