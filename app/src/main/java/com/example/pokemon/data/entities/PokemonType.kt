package com.example.pokemon.data.entities

import com.google.gson.annotations.SerializedName

data class PokemonType(
    @SerializedName("type")
    val type: TypeApiModel
)
