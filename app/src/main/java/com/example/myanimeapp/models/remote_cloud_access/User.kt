package com.example.myanimeapp.models.remote_cloud_access

data class User(
    var uid: String ?= null,
    var email: String ?= null,
    var animes: MutableList<Int> = mutableListOf()
)