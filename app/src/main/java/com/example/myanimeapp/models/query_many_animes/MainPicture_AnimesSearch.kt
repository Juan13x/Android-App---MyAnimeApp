package com.example.myanimeapp.models.query_many_animes


import com.google.gson.annotations.SerializedName

data class MainPicture_AnimesSearch(
    @SerializedName("large")
    val large: String,
    @SerializedName("medium")
    val medium: String
)