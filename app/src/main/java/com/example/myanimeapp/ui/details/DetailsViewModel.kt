package com.example.myanimeapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myanimeapp.models.errors.ErrorData
import com.example.myanimeapp.models.errors.e_Error
import com.example.myanimeapp.models.persistent.PersistentEntityMyAnime
import com.example.myanimeapp.models.search_fragment.SearchFragmentDataForAnimes
import com.example.myanimeapp.persistent.LocalRepository
import com.example.myanimeapp.remote_access.MyRemoteRepository
import com.example.myanimeapp.remote_access.e_StatusResult
import com.example.myanimeapp.remote_access.webServices.ApiRepository
import com.example.myanimeapp.ui.e_Interfaces
import com.example.myanimeapp.ui.shared.SharedViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailsViewModel() : ViewModel() {
    private val operationFinishedMutableLiveData: MutableLiveData<Boolean?> = MutableLiveData()
    val operationFinishedLiveData: LiveData<Boolean?> = operationFinishedMutableLiveData

    private val errorMutableLiveData: MutableLiveData<ErrorData?> = MutableLiveData()
    val errorLiveData: LiveData<ErrorData?> = errorMutableLiveData

    private val remoteRepository = MyRemoteRepository().getInstanceRepository()
    private val localRepository = LocalRepository()
    private var job: Job? = null

    fun saveAnime(anime: PersistentEntityMyAnime,
                  animePos: Int,
                  interfaceState: e_Interfaces,
                  sharedModel: SharedViewModel){
        if(job == null){
            job = viewModelScope.launch {
                val oldAnimes = localRepository.getAnimesId()
                val animeId = anime.id
                oldAnimes.add(animeId)
                val resultFireStore = remoteRepository.updateAnimeList(oldAnimes)
                if(resultFireStore.status == e_StatusResult.success){
                    if(interfaceState == e_Interfaces.MY_ANIMES){
                        updateFavoriteStateAnimeList(sharedModel.animeListSearch, animeId)
                    }else{
                        sharedModel.animeListSearch[animePos].stateFavorites = true
                    }
                    localRepository.saveOneAnime(anime)
                    sharedModel.animeListMyAnimes.add(anime)
                    updateFavoriteStateAnimeList(sharedModel.airingAnimes, animeId)
                    operationFinishedMutableLiveData.postValue(true)
                }else{
                    resultFireStore.errorException?.let { exception ->
                        exception.message?.let {
                            errorMutableLiveData.postValue(
                                ErrorData(
                                    errorEvent = e_Error.GenericStr,
                                    errorMessageStr = exception.message
                                )
                            )
                        }
                    }
                }
                job = null
            }
        }
    }

    fun deleteAnime(anime: PersistentEntityMyAnime,
                  animePos: Int,
                  interfaceState: e_Interfaces,
                  sharedModel: SharedViewModel){
        if(job == null){
            job = viewModelScope.launch {
                val oldAnimes = localRepository.getAnimesId()
                val animeId = anime.id
                oldAnimes.remove(animeId)
                val resultFireStore = remoteRepository.updateAnimeList(oldAnimes)
                if(resultFireStore.status == e_StatusResult.success){
                    if(interfaceState == e_Interfaces.MY_ANIMES){
                        updateNoMoreFavoriteStateAnimeList(sharedModel.animeListSearch, animeId)
                    }else{
                        sharedModel.animeListSearch[animePos].stateFavorites = false
                    }
                    localRepository.deleteAnime(anime)
                    sharedModel.removeAnimeFromFavorites(animeId)
                    updateNoMoreFavoriteStateAnimeList(sharedModel.airingAnimes, animeId)
                    operationFinishedMutableLiveData.postValue(false)
                }else{
                    resultFireStore.errorException?.let { exception ->
                        exception.message?.let {
                            errorMutableLiveData.postValue(
                                ErrorData(
                                    errorEvent = e_Error.GenericStr,
                                    errorMessageStr = exception.message
                                )
                            )
                        }
                    }
                }
                job = null
            }
        }
    }

    private fun updateFavoriteStateAnimeList(animesStruct: ArrayList<SearchFragmentDataForAnimes>, id: Int) {
        animesStruct.forEach {struct ->
            val anime = struct.anime
            if(anime.id == id) {
                struct.stateFavorites = true
                return@forEach
            }
        }
    }

    private fun updateNoMoreFavoriteStateAnimeList(animesStruct: ArrayList<SearchFragmentDataForAnimes>, id: Int) {
        animesStruct.forEach {struct ->
            val anime = struct.anime
            if(anime.id == id) {
                struct.stateFavorites = false
                return@forEach
            }
        }
    }

    fun resetData() {
        errorMutableLiveData.value = null
        operationFinishedMutableLiveData.value = null
    }
}