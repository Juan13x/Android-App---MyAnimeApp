package com.example.myanimeapp.remote_access

import com.example.myanimeapp.remote_access.remote_repositories.FirebaseRepository
import com.example.myanimeapp.remote_access.remote_repositories.RemoteRepository

class MyRemoteRepository {
    private enum class Repositories{
        FIREBASE,
        AWS
    }
    private val currentRepository = Repositories.FIREBASE

    fun getInstanceRepository(): RemoteRepository{
        return when(currentRepository){
            Repositories.FIREBASE -> FirebaseRepository()
            else -> FirebaseRepository()
        }
    }
}