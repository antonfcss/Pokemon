package com.example.pokemon.data

import com.google.gson.annotations.SerializedName

data class SpritesApiModel(
    @SerializedName("front_default")
    val frontImage: String,
)