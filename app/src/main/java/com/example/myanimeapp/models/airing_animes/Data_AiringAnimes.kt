package com.example.myanimeapp.models.airing_animes


import com.google.gson.annotations.SerializedName

data class Data_AiringAnimes(
    @SerializedName("node")
    val nodeAiringAnimes: Node_AiringAnimes,
    @SerializedName("ranking")
    val ranking: Ranking_AiringAnimes
)