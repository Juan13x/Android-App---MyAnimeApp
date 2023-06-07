package com.example.myanimeapp.models.one_anime


import com.google.gson.annotations.SerializedName

data class AlternativeTitles_AnimeData(
    @SerializedName("en")
    val en: String,
    @SerializedName("ja")
    val ja: String,
    @SerializedName("synonyms")
    val synonyms: List<String>
)