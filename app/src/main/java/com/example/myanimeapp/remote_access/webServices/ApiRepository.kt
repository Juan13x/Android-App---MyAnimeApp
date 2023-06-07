package com.example.myanimeapp.remote_access.webServices

import com.example.myanimeapp.models.airing_animes.AiringAnimesData
import com.example.myanimeapp.models.airing_animes.Data_AiringAnimes
import com.example.myanimeapp.models.one_anime.AnimeData
import com.example.myanimeapp.models.query_many_animes.AnimesSearch
import com.example.myanimeapp.models.query_many_animes.Data_AnimesSearch
import com.example.myanimeapp.remote_access.RemoteResult

class ApiRepository {
    val api = "37d9f0c0a3be9e5364b5ad65c08f873b"

    suspend fun getAnimesAiring(): RemoteResult<AiringAnimesData>{
        return try {
            val airingAnimes = AnimeDB.retrofit.getAnimesAiring(clientID = api)
            RemoteResult.Success(airingAnimes)
        }catch(exception: Exception){
            RemoteResult.Error(
                exception = exception,
                errorMessage = exception.message)
            }
    }
    suspend fun getAnimeDetails(id: Int): RemoteResult<AnimeData>{
        return try {
            val animeDetails = AnimeDB.retrofit.getAnimeDetails(clientID = api, ID = id)
            RemoteResult.Success(animeDetails)
        }catch(exception: Exception){
            RemoteResult.Error(
                exception = exception,
                errorMessage = exception.message)
        }
    }
    suspend fun getAnimesSearch(search: String): RemoteResult<AnimesSearch>{
        return try {
            val search = AnimeDB.retrofit.getAnimesSearch(clientID = api, search)
            RemoteResult.Success(search)
        }catch(exception: Exception){
            RemoteResult.Error(
                exception = exception,
                errorMessage = exception.message)
        }
    }

    fun getIdFromDataFromAiringAnimesData(data: Data_AiringAnimes) = data.nodeAiringAnimes.id
    fun getIdFromDataFromAnimesSearch(data: Data_AnimesSearch) = data.nodeAnimesSearch.id
}