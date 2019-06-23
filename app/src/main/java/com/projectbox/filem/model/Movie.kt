package com.projectbox.filem.model

import com.google.gson.annotations.SerializedName

/**
 * Created by adinugroho
 */
data class Movie(
    val id: String,
    @SerializedName("vote_average")
    val vote: Double,
    @SerializedName("vote_count")
    val voteCount: Double,
    val title: String,
    val overview: String,
    @SerializedName("poster_path")
    val poster: String,
    @SerializedName("release_date")
    val releaseDate: String
)

data class MovieResponse(
    val results: List<Movie>
)