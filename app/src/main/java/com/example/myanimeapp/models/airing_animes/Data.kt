package com.example.myanimeapp.models.airing_animes


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("node")
    val node: Node,
    @SerializedName("ranking")
    val ranking: Ranking
)