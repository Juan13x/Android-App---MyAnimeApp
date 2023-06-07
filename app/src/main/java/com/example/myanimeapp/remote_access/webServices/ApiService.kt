package com.example.myanimeapp.remote_access.webServices

import com.example.myanimeapp.models.airing_animes.AiringAnimesData
import com.example.myanimeapp.models.one_anime.AnimeData
import com.example.myanimeapp.models.query_many_animes.AnimesSearch
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("anime/ranking?ranking_type=airing")
    suspend fun getAnimesAiring(@Header ("X-MAL-CLIENT-ID") clientID: String): AiringAnimesData
    @GET("anime/{ID}?fields=id,title,main_picture,alternative_titles,synopsis,mean,status,genres,num_episodes,start_season,studios")
    suspend fun getAnimeDetails(@Header ("X-MAL-CLIENT-ID") clientID: String, @Path ("ID") ID: Int): AnimeData
    @GET("anime?limit=10")
    suspend fun getAnimesSearch(@Header ("X-MAL-CLIENT-ID") clientID: String, @Query ("q") q: String): AnimesSearch

}