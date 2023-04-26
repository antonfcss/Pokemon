package com.example.pokemon.data.entities

import com.google.gson.annotations.SerializedName

data class TypeApiModel(
    @SerializedName("name")
    val typeName: String
)
