package com.example.pokemon.data.entities

import com.google.gson.annotations.SerializedName

data class PokemonApiModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
