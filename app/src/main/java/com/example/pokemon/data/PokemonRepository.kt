package com.example.pokemon.data

import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonApi: PokemonApi
) {
    suspend fun getPokemonListFromApi() = flow {
        emit(pokemonApi.getPokemon())
    }
}