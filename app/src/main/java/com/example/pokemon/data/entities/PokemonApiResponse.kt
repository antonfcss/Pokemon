package com.example.pokemon.data.entities

import com.google.gson.annotations.SerializedName

data class PokemonApiResponse(
    @SerializedName("results")
    val pokemonList: List<PokemonApiModel>
)