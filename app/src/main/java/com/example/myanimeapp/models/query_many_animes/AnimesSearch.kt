package com.example.myanimeapp.models.query_many_animes


import com.google.gson.annotations.SerializedName

data class AnimesSearch(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("paging")
    val paging: Paging
)