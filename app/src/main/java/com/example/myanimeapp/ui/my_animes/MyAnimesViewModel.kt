package com.example.myanimeapp.ui.my_animes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myanimeapp.remote_access.MyRemoteRepository
import com.example.myanimeapp.remote_access.e_StatusResult
import com.example.myanimeapp.remote_access.remote_repositories.RemoteRepository
import com.example.myanimeapp.remote_access.webServices.ApiRepository
import com.example.myanimeapp.models.errors.ErrorData
import com.example.myanimeapp.models.errors.e_Error
import com.example.myanimeapp.models.persistent.PersistentEntityMyAnime
import com.example.myanimeapp.persistent.LocalRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyAnimesViewModel : ViewModel() {

    private val remoteRepository : RemoteRepository = MyRemoteRepository().getInstanceRepository()
    private val apiRepository = ApiRepository()
    private val localRepository = LocalRepository()

    private val changeAdapterMutableLiveData: MutableLiveData<ArrayList<PersistentEntityMyAnime>?> = MutableLiveData()
    val changeAdapterLiveData: LiveData<ArrayList<PersistentEntityMyAnime>?> = changeAdapterMutableLiveData

    private val errorMutableLiveData: MutableLiveData<ErrorData?> = MutableLiveData()
    val errorLiveData: LiveData<ErrorData?> = errorMutableLiveData

    private val reloadMutableLiveData: MutableLiveData<Boolean?> = MutableLiveData()
    val reloadLiveData: LiveData<Boolean?> = reloadMutableLiveData

    private var job: Job? = null
    fun updateMyAnimes(){
        if(job == null){
            job = viewModelScope.launch {
                try {
                    val savedAnimes = localRepository.getAllFavoriteAnimes()
                    if (savedAnimes.isEmpty()) {
                        val userId = remoteRepository.getCurrentUserId()!!
                        val cloudResult = remoteRepository.getAnimesFromUser(userId)
                        if (cloudResult.status == e_StatusResult.success) {
                            val animes = ArrayList<PersistentEntityMyAnime>()
                            if (cloudResult.data!!.isNotEmpty()) {
                                cloudResult.data!!.iterator().forEach { animeId ->
                                    val apiResult = apiRepository.getAnimeDetails(animeId)
                                    if (apiResult.status == e_StatusResult.success) {
                                        val animeDetails = apiResult.data!!
                                        animes.add(
                                            localRepository.animeRemoteDataToLocalData(animeDetails)
                                        )
                                    } else {
                                        throw apiResult.errorException!!
                                    }
                                }
                                localRepository.saveAnimes(animes)
                                changeAdapterMutableLiveData.postValue(animes)
                            }
                        } else {
                            cloudResult.errorException?.let {exception->
                                if(!exception.message.isNullOrBlank()) {
                                    errorMutableLiveData.postValue(
                                        ErrorData(
                                            errorEvent = e_Error.GenericStr,
                                            errorMessageStr = cloudResult.errorMessage
                                        )
                                    )
                                }
                            }
                        }
                    } else {
                        changeAdapterMutableLiveData.postValue(savedAnimes)
                    }
                } catch(exception: Exception){
                    if(!exception.message.isNullOrBlank()) {
                        errorMutableLiveData.postValue(
                            ErrorData(
                                errorEvent = e_Error.GenericStr,
                                errorMessageStr = exception.message
                            )
                        )
                    }
                }
                job = null
            }
        }
    }

    fun isNecessaryToReload(animeList: ArrayList<PersistentEntityMyAnime>){
        if(animeList.isEmpty()) updateMyAnimes()
        else {
            if(job == null)
                reloadMutableLiveData.value = true
        }
    }

    fun resetLiveData() {
        changeAdapterMutableLiveData.value = null
        errorMutableLiveData.value = null
        reloadMutableLiveData.value = null
    }
}