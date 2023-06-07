package com.example.myanimeapp.ui.register.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myanimeapp.R
import com.example.myanimeapp.remote_access.MyRemoteRepository
import com.example.myanimeapp.remote_access.e_StatusResult
import com.example.myanimeapp.remote_access.remote_repositories.RemoteRepository
import com.example.myanimeapp.models.errors.ErrorData
import com.example.myanimeapp.models.errors.e_Error
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel: ViewModel() {
    private val errorMutableLiveData: MutableLiveData<ErrorData> = MutableLiveData()
    val errorLiveData: LiveData<ErrorData> = errorMutableLiveData

    private val successMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val successLiveData: LiveData<Boolean> = successMutableLiveData

    private val loadingStateMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val loadingStateLiveData: LiveData<Boolean> = loadingStateMutableLiveData

    private val isLoggedInMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isLoggedLiveData: LiveData<Boolean> = isLoggedInMutableLiveData

    private val remoteRepository : RemoteRepository = MyRemoteRepository().getInstanceRepository()

    fun isThereCurrentUser(): Boolean =
        runBlocking { remoteRepository.isThereCurrentUser() }

    // A placeholder username validation check
    private fun isUserNameValid(userEmail: String): Boolean {
        val containEmail = userEmail.contains('@')
        return if (!containEmail)
            false
        else
            Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    private fun areFieldsOk(email: String, password: String): Boolean{
        return if (!isUserNameValid(email)){
            errorMutableLiveData.value = ErrorData(
                errorEvent = e_Error.Email,
                errorMessageInt = R.string.general_email_error
            )
            false
        }
        else if (!isPasswordValid(password)){
            errorMutableLiveData.value = ErrorData(
                errorEvent = e_Error.Password,
                errorMessageInt = R.string.general_password_error
            )
            false
        }
        else
            true
    }

    fun login(email: String, password: String){
        if(areFieldsOk(email, password)){
            loadingStateMutableLiveData.value = true
            viewModelScope.launch {
                val result = remoteRepository.loginUser(email, password)
                if(result.status == e_StatusResult.success){
                    //TODO: Save User Info with persistent assistant
                    successMutableLiveData.postValue(true)
                }
                else{
                    errorMutableLiveData.postValue(
                        ErrorData(
                            errorEvent = e_Error.GenericStr,
                            errorMessageStr = result.errorMessage
                        )
                    )
                }
            }
        }
    }
}