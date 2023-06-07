package com.example.myanimeapp.ui.register.signup

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myanimeapp.R
import com.example.myanimeapp.remote_access.MyRemoteRepository
import com.example.myanimeapp.remote_access.remote_repositories.RemoteRepository
import com.example.myanimeapp.remote_access.e_StatusResult
import com.example.myanimeapp.models.errors.ErrorData
import com.example.myanimeapp.models.errors.e_Error
import kotlinx.coroutines.launch

class SignupViewModel: ViewModel() {
    private val errorMutableLiveData: MutableLiveData<ErrorData> = MutableLiveData()
    val errorLiveData: LiveData<ErrorData> = errorMutableLiveData

    private val successMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val successLiveData: LiveData<Boolean> = successMutableLiveData

    private val loadingStateMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val loadingStateLiveData: LiveData<Boolean> = loadingStateMutableLiveData

    private val remoteRepository : RemoteRepository = MyRemoteRepository().getInstanceRepository()

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

    private fun areFieldsOk(email: String, password: String, repPassword: String): Boolean{
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
        else if(!isPasswordValid(repPassword)){
            errorMutableLiveData.value = ErrorData(
                errorEvent = e_Error.RepPassword,
                errorMessageInt = R.string.general_password_error
            )
            false
        }
        else
            true
    }

    fun signUp(email: String, password: String, repPassword: String){
        if(areFieldsOk(email, password, repPassword)){
            if(password == repPassword) {
                loadingStateMutableLiveData.value = true
                viewModelScope.launch {
                    val result = remoteRepository.signUpUser(email, password)
                    if(result.status == e_StatusResult.success){
                        successMutableLiveData.postValue(true)
                    }
                    else
                        errorMutableLiveData.postValue(
                            ErrorData(
                                errorEvent = e_Error.GenericStr,
                                errorMessageStr = result.errorMessage
                            )
                        )
                }
            }
            else{
                errorMutableLiveData.value =
                    ErrorData(
                        e_Error.GenericInt,
                        R.string.general_differentPasswords_error
                    )
            }
        }
    }
}