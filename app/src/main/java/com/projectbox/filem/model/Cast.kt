package com.projectbox.filem.model

import com.google.gson.annotations.SerializedName

/**
 * Created by adinugroho
 */
data class Cast(
    val character: String,
    val name: String,
    @SerializedName("profile_path") val image: String
)

data class CastResponse(
    val id: Int,
    val cast: List<Cast>
)