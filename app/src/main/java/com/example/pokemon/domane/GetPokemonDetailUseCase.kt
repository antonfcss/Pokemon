package com.example.pokemon.domane

import com.example.pokemon.data.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend fun getPokemonDetail(url: String): Flow<PokemonModelDetail> {
        val idRegex = Regex("/(\\d+)/?$")
        val id = idRegex.find(url)
        if (id != null) {
            return pokemonRepository.getDetailPokemon(
                id.groupValues.getOrNull(1)?.toIntOrNull()
                    ?: throw RuntimeException("Регулярка сломалась")
            )
        }
        throw RuntimeException("Регулярка сломалась")
    }
}
