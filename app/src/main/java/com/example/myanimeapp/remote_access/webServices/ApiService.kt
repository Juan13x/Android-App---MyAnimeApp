package com.example.myanimeapp.remote_access.webServices

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("anime/ranking")
    fun getAnimesAiring(@Query ("ranking_type") ranking_type: String = "airing", @Header ("X-MAL-CLIENT-ID") clientID: String): List<AnimeData>
}