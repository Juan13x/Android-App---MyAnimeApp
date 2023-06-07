package com.example.myanimeapp.models.airing_animes


import com.google.gson.annotations.SerializedName

data class Paging_AiringAnimes(
    @SerializedName("next")
    val next: String
)