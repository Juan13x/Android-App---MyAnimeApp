package com.example.myanimeapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myanimeapp.persistent.LocalRepository
import com.example.myanimeapp.remote_access.MyRemoteRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileViewModel : ViewModel() {
    private val remoteRepository = MyRemoteRepository().getInstanceRepository()
    private val localRepository = LocalRepository()
    fun logout() {
        viewModelScope.launch {
            localRepository.deleteAll()
            remoteRepository.logout()
        }
    }

    fun getUserEmail(): String = runBlocking { remoteRepository.getCurrentUserId()!! }


}