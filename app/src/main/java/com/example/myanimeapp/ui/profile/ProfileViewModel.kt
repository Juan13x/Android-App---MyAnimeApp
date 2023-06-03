package com.example.myanimeapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myanimeapp.remote_access.MyRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileViewModel : ViewModel() {
    private val remoteRepository = MyRepository().getInstanceRepository()
    fun logout() {
        runBlocking {
            remoteRepository.logout()
        }
    }

}