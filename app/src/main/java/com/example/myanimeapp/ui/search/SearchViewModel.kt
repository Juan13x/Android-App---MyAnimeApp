package com.example.myanimeapp.ui.search

import androidx.lifecycle.*
import com.example.myanimeapp.models.errors.ErrorData
import com.example.myanimeapp.models.errors.e_Error
import com.example.myanimeapp.models.one_anime.AnimeData
import com.example.myanimeapp.models.search_fragment.SearchFragmentDataForAnimes
import com.example.myanimeapp.persistent.LocalRepository
import com.example.myanimeapp.remote_access.e_StatusResult
import com.example.myanimeapp.remote_access.webServices.ApiRepository
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private var query: String = ""

    private val errorMutableLiveData: MutableLiveData<ErrorData?> = MutableLiveData()
    val errorLiveData: LiveData<ErrorData?> = errorMutableLiveData

    private val changeWithSearchAdapterMutableLiveData: MutableLiveData<ArrayList<SearchFragmentDataForAnimes>?> = MutableLiveData()
    val changeWithSearchAdapterLiveData: LiveData<ArrayList<SearchFragmentDataForAnimes>?> = changeWithSearchAdapterMutableLiveData

    private val changeWithAiringAnimesAdapterMutableLiveData: MutableLiveData<ArrayList<SearchFragmentDataForAnimes>?> = MutableLiveData()
    val changeWithAiringAnimesAdapterLiveData: LiveData<ArrayList<SearchFragmentDataForAnimes>?> = changeWithAiringAnimesAdapterMutableLiveData

    private val reloadMutableLiveData: MutableLiveData<Boolean?> = MutableLiveData()
    val reloadLiveData: LiveData<Boolean?> = reloadMutableLiveData

    private val apiRepository = ApiRepository()
    private val localRepository = LocalRepository()

    private var job: Job? = null
    fun loadAiringAnimes(){
        if(job == null) {
            job = viewModelScope.launch {
                try {
                    val apiResult = apiRepository.getAnimesAiring()
                    if (apiResult.status == e_StatusResult.success) {
                        val airingAnimes = apiResult.data!!
                        val animes = ArrayList<SearchFragmentDataForAnimes>()
                        airingAnimes.data.forEach { animeData ->
                            val animeId = apiRepository.getIdFromDataFromAiringAnimesData(animeData)
                            val apiResult2 = apiRepository.getAnimeDetails(animeId)
                            if (apiResult2.status == e_StatusResult.success) {
                                val animeDetails = apiResult2.data!!
                                val stateFavorites = localRepository.isAnimeSaved(animeDetails.id)
                                val localAnimeFormat = localRepository.animeRemoteDataToLocalData(animeDetails)
                                animes.add(
                                    SearchFragmentDataForAnimes(
                                        localAnimeFormat,
                                        stateFavorites
                                    )
                                )
                            } else {
                                throw apiResult.errorException!!
                            }
                        }
                        changeWithAiringAnimesAdapterMutableLiveData.postValue(animes)

                    } else {
                        apiResult.errorException?.let { exception ->
                            if (!exception.message.isNullOrBlank()) {
                                errorMutableLiveData.postValue(
                                    ErrorData(
                                        errorEvent = e_Error.GenericStr,
                                        errorMessageStr = exception.message
                                    )
                                )
                            }
                        }

                    }
                } catch (exception: Exception) {
                    if (!exception.message.isNullOrBlank()) {
                        errorMutableLiveData.value = ErrorData(
                            errorEvent = e_Error.GenericStr,
                            errorMessageStr = exception.message
                        )
                    }

                }
                job = null
            }
        }
    }

    fun resetLiveData() {
        changeWithSearchAdapterMutableLiveData.value = null
        changeWithAiringAnimesAdapterMutableLiveData.value = null
        errorMutableLiveData.value = null
        reloadMutableLiveData.value = null
    }

    fun isNecessaryToReload(animeList: ArrayList<SearchFragmentDataForAnimes>){
        if(animeList.isEmpty())
            loadAiringAnimes()
        else
            if(job == null)
                reloadMutableLiveData.value = true
    }

    fun searchAnimes(query: String){
        this.query = query
        if(job == null){
            job = viewModelScope.launch {
                    try {
                        val apiResult = apiRepository.getAnimesSearch(query)
                        if (apiResult.status == e_StatusResult.success) {
                            val animesSearched = apiResult.data!!
                            val animes = ArrayList<SearchFragmentDataForAnimes>()
                            animesSearched.data.forEach { animeData ->
                                val animeId = apiRepository.getIdFromDataFromAnimesSearch(animeData)
                                val apiResult2 = apiRepository.getAnimeDetails(animeId)
                                if (apiResult2.status == e_StatusResult.success) {
                                    val animeDetails = apiResult2.data!!
                                    val stateFavorites = localRepository.isAnimeSaved(animeDetails.id)
                                    val localAnimeFormat = localRepository.animeRemoteDataToLocalData(animeDetails)
                                    animes.add(
                                        SearchFragmentDataForAnimes(
                                            localAnimeFormat,
                                            stateFavorites
                                        )
                                    )
                                } else {
                                    throw apiResult.errorException!!
                                }
                            }
                            changeWithSearchAdapterMutableLiveData.postValue(animes)

                        } else {
                            apiResult.errorException?.let {exception ->
                                if(!exception.message.isNullOrBlank()) {
                                    errorMutableLiveData.postValue(
                                        ErrorData(
                                            errorEvent = e_Error.GenericStr,
                                            errorMessageStr = exception.message
                                        )
                                    )
                                }
                            }
                        }
                    } catch (exception: Exception) {
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

    fun getQuery(): String {
        return query
    }
}