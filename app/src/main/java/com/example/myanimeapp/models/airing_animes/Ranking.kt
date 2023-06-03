package com.example.myanimeapp.models.airing_animes


import com.google.gson.annotations.SerializedName

data class Ranking(
    @SerializedName("rank")
    val rank: Int
)