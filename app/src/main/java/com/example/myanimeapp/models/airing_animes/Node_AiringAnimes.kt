package com.example.myanimeapp.models.airing_animes


import com.google.gson.annotations.SerializedName

data class Node_AiringAnimes(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main_picture")
    val mainPictureAiringAnimes: MainPicture_AiringAnimes,
    @SerializedName("title")
    val title: String
)