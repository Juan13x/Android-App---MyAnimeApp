package com.example.myanimeapp.models.query_many_animes


import com.google.gson.annotations.SerializedName

data class MainPicture(
    @SerializedName("large")
    val large: String,
    @SerializedName("medium")
    val medium: String
)