package com.example.myanimeapp.models.one_anime


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AnimeData(
    @SerializedName("alternative_titles")
    val alternativeTitlesAnimeData: AlternativeTitles_AnimeData,
    @SerializedName("genres")
    val genreAnimeData: List<Genre_AnimeData>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main_picture")
    val mainPictureAnimeData: MainPicture_AnimeData,
    @SerializedName("mean")
    val mean: Double,
    @SerializedName("num_episodes")
    val numEpisodes: Int,
    @SerializedName("start_season")
    val startSeasonAnimeData: StartSeason_AnimeData,
    @SerializedName("status")
    val status: String,
    @SerializedName("studios")
    val studioAnimeData: List<Studio_AnimeData>,
    @SerializedName("synopsis")
    val synopsis: String,
    @SerializedName("title")
    val title: String
): Serializable