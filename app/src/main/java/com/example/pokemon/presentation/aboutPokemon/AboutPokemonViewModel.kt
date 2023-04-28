package com.example.pokemon.presentation.aboutPokemon

import android.util.Log
import com.example.pokemon.base.BaseViewModel
import com.example.pokemon.base.ViewState
import com.example.pokemon.domane.GetPokemonDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class AboutPokemonViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase
) : BaseViewModel<AboutPokemonState>() {

    fun getPokemonDetail(url: String) {
        launchIO {
            getPokemonDetailUseCase.getPokemonDetail(url)
                .onStart { updateState(ViewState.Loading) }
                .catch { updateState(ViewState.Error(it.message.toString())) }
                .collect { pokemonModelDetail ->
                    if (pokemonModelDetail.isNotEmpty()) {
                        updateState(ViewState.Success(AboutPokemonState(pokemonModelDetail)))
                    } else {
                        updateState(ViewState.Error("Empty data"))
                    }
                }
        }

    }
}
