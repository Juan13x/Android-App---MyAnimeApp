package com.example.myanimeapp.ui.my_animes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myanimeapp.models.one_anime.AnimeData
import com.example.myanimeapp.remote_access.MyRepository
import com.example.myanimeapp.remote_access.e_StatusResult
import com.example.myanimeapp.remote_access.remote_repositories.RemoteRepository
import com.example.myanimeapp.ui.register.RegisterErrorData
import com.example.myanimeapp.ui.register.e_RegisterError
import kotlinx.coroutines.launch

class MyAnimesViewModel : ViewModel() {

    private var myAnimes: ArrayList<AnimeData> = arrayListOf()
    private val remoteRepository : RemoteRepository = MyRepository().getInstanceRepository()

    private val changeAdapterMutableLiveData: MutableLiveData<ArrayList<AnimeData>> = MutableLiveData()
    val changeAdapterLiveData: LiveData<ArrayList<AnimeData>> = changeAdapterMutableLiveData

    private val errorMutableLiveData: MutableLiveData<RegisterErrorData> = MutableLiveData()
    val errorLiveData: LiveData<RegisterErrorData> = errorMutableLiveData

    fun updateMyAnimes(){
        viewModelScope.launch {
            //TODO: Change With persistent Access
            val userId = remoteRepository.getCurrentUserId()!!
            val result = remoteRepository.getAnimesFromUser(userId)
            if(result.status == e_StatusResult.success){
                val animesTest = mutableListOf<AnimeData>()
                if(result.data!!.isNotEmpty()) {
                    result.data!!.iterator().forEach { animeId ->
                        animesTest.add(test)
                    }
                    changeAdapterMutableLiveData.postValue(ArrayList(animesTest))
                }
            }
            else{
                errorMutableLiveData.postValue(
                    RegisterErrorData(
                        errorEvent = e_RegisterError.GenericStr,
                        errorMessageStr = result.errorMessage
                    )
                )
            }
        }
    }
}