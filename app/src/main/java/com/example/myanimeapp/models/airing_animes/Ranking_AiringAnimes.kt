package com.example.myanimeapp.models.airing_animes


import com.google.gson.annotations.SerializedName

data class Ranking_AiringAnimes(
    @SerializedName("rank")
    val rank: Int
)