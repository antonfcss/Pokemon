package com.example.pokemon.data

import com.google.gson.annotations.SerializedName

data class PokemonApiResponse(
    @SerializedName("results")
    val pokemonList: List<PokemonApiModel>
)