package com.example.myanimeapp.models.query_many_animes


import com.google.gson.annotations.SerializedName

data class Paging_AnimesSearch(
    @SerializedName("next")
    val next: String
)