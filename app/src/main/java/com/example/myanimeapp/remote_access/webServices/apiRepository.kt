package com.example.myanimeapp.remote_access.webServices

class apiRepository {
    val api = "37d9f0c0a3be9e5364b5ad65c08f873b"

    suspend fun getAnimesAiring(): List<AnimeData> = AnimeDB.retrofit.getAnimesAiring(clientID = api)
}