package com.example.myanimeapp.models.one_anime


import com.google.gson.annotations.SerializedName

data class StartSeason_AnimeData(
    @SerializedName("season")
    val season: String,
    @SerializedName("year")
    val year: Int
)