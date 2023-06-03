package com.example.myanimeapp.models.airing_animes


import com.google.gson.annotations.SerializedName

data class Paging(
    @SerializedName("next")
    val next: String
)