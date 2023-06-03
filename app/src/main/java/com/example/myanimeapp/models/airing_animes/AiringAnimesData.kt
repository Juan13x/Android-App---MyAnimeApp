package com.example.myanimeapp.models.airing_animes


import com.google.gson.annotations.SerializedName

data class AiringAnimesData(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("paging")
    val paging: Paging
)