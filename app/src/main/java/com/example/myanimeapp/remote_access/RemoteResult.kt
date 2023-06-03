package com.example.myanimeapp.remote_access

enum class e_StatusResult{
    success,
    failure
}

sealed class RemoteResult<T>(
    var data: T ?= null,
    var errorException: Exception ?= null,
    var errorMessage: String ?= null,
    var status: e_StatusResult ?= null
){
    class Success<T>(data: T):
        RemoteResult<T>(
            data = data,
            status = e_StatusResult.success)

    class Error<T>(exception : Exception ?= null, errorMessage: String ?= null):
        RemoteResult<T>(
            errorException = exception,
            errorMessage = errorMessage,
            status = e_StatusResult.failure)
}
