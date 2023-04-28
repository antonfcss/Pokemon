package com.example.pokemon.presentation

import com.example.pokemon.base.BaseViewModel
import com.example.pokemon.base.ViewState
import com.example.pokemon.domane.GetPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val getPokemonUseCase: GetPokemonUseCase
) : BaseViewModel<StartState>() {

    fun getPokemonList() {
        launchIO {
            getPokemonUseCase.getData()
                .onStart { updateState(ViewState.Loading) }
                .catch { updateState(ViewState.Error(it.message.toString())) }
                .collect { pokemonList ->
                    updateState(ViewState.Success(StartState(pokemonList)))
                }
        }
    }
}
