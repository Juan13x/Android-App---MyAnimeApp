package com.example.myanimeapp.models.one_anime


import com.google.gson.annotations.SerializedName

data class AnimeData(
    @SerializedName("alternative_titles")
    val alternativeTitles: AlternativeTitles,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main_picture")
    val mainPicture: MainPicture,
    @SerializedName("mean")
    val mean: Double,
    @SerializedName("num_episodes")
    val numEpisodes: Int,
    @SerializedName("start_season")
    val startSeason: StartSeason,
    @SerializedName("status")
    val status: String,
    @SerializedName("synopsis")
    val synopsis: String,
    @SerializedName("title")
    val title: String
)