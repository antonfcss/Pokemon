package com.example.pokemon.data

import com.google.gson.annotations.SerializedName

data class PokemonApiDetail(
    @SerializedName("sprites")
    val spritesApiModel: SpritesApiModel
)