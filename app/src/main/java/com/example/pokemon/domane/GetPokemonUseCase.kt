package com.example.pokemon.domane

import com.example.pokemon.data.PokemonRepository
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend fun  getData() = pokemonRepository.getPokemonListFromApi()
//    suspend fun getData(): Flow<Response<PokemonApiResponse>> {
//        return pokemonRepository.getPokemonListFromApi()
//    }
}