package com.example.pokemon.data.entities

import com.google.gson.annotations.SerializedName

data class PokemonApiDetail(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("weight")
    val weight: Int,
    @SerializedName("sprites")
    val spritesApiModel: SpritesApiModel,
    @SerializedName("types")
    val types: List<PokemonType>
)