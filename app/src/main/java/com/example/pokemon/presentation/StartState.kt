package com.example.pokemon.presentation

import androidx.paging.PagingData
import com.example.pokemon.domane.PokemonModel

data class StartState(
    val startModelList: PagingData<PokemonModel>
)