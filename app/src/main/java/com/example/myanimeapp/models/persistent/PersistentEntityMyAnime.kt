package com.example.myanimeapp.models.persistent

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_animes")
data class PersistentEntityMyAnime(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name="alternative_titles_anime_Data") var alternativeTitlesAnimeData: ArrayList<String>,
    @ColumnInfo(name="genre_anime_data") var genreAnimeData: ArrayList<String>,
    @ColumnInfo(name="main_picture_anime_data") val mainPictureAnimeData: String,
    @ColumnInfo(name="mean") val mean: Double,
    @ColumnInfo(name="num_episodes") val numEpisodes: Int,
    @ColumnInfo(name="start_season_anim_data") val startSeasonAnimeData: String,
    @ColumnInfo(name="year") val year: Int,
    @ColumnInfo(name="status") val status: String,
    @ColumnInfo(name="studio_anime_data") var studioAnimeData: ArrayList<String>,
    @ColumnInfo(name="synopsis") val synopsis: String,
    @ColumnInfo(name="title") val title: String

)