package com.example.myanimeapp.models.query_many_animes


import com.google.gson.annotations.SerializedName

data class Node_AnimesSearch(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main_picture")
    val mainPictureAnimesSearch: MainPicture_AnimesSearch,
    @SerializedName("title")
    val title: String
)