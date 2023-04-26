package com.example.pokemon.domane

data class PokemonModelDetail(
    val name: String,
    val height: Int,
    val weight: Double,
    val spritesApiModel: SpritesModel,
    val types: List<PokemonType>
)