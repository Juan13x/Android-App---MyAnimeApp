package com.example.myanimeapp.models

data class User(
    var uid: String ?= null,
    var email: String ?= null,
    var animes: MutableList<Int> = mutableListOf()
)