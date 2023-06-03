package com.example.myanimeapp.models.airing_animes


import com.google.gson.annotations.SerializedName

data class Node(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main_picture")
    val mainPicture: MainPicture,
    @SerializedName("title")
    val title: String
)