package com.example.myanimeapp.remote_access.remote_repositories

import com.example.myanimeapp.models.remote_cloud_access.User
import com.example.myanimeapp.remote_access.RemoteResult

interface RemoteRepository {
    suspend fun isThereCurrentUser() : Boolean
    suspend fun getCurrentUserId() : String?
    suspend fun getAnimesFromUser(email: String): RemoteResult<MutableList<Int>>
    suspend fun loginUser(email: String, password: String) : RemoteResult<User>
    suspend fun signUpUser(email: String, password: String) : RemoteResult<User>
    suspend fun updateAnimeList(animes: ArrayList<Int>) : RemoteResult<Boolean>
    suspend fun logout()
}