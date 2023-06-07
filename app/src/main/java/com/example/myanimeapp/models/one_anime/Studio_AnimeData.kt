package com.example.myanimeapp.models.one_anime


import com.google.gson.annotations.SerializedName

data class Studio_AnimeData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)