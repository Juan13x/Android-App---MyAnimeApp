package com.example.myanimeapp.models.query_many_animes


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("node")
    val node: Node
)