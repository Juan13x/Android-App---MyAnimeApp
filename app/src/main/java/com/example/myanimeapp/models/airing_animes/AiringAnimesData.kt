package com.example.myanimeapp.models.airing_animes


import com.google.gson.annotations.SerializedName

data class AiringAnimesData(
    @SerializedName("data")
    val data: List<Data_AiringAnimes>,
    @SerializedName("paging")
    val pagingAiringAnimes: Paging_AiringAnimes
)