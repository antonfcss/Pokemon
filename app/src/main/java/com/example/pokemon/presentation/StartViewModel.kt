package com.example.pokemon.presentation

import android.util.Log
import com.example.pokemon.domane.GetPokemonUseCase
import com.example.pokemon.presentation.adapter.StartModel
import com.pult.application.base.BaseViewModel
import com.pult.application.base.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val getPokemonUseCase: GetPokemonUseCase
) : BaseViewModel<StartState>() {

    fun getPokemonList() {
        launchIO {
            getPokemonUseCase.getData()
                .catch { Log.d("errorOnGetProgramsList", it.message.toString()) }
                .collect { pokemonList ->
                    updateState(
                        ViewState.Success(
                            StartState(
                                pokemonList.map { pokemonModel ->
                                    StartModel(
                                        name = pokemonModel.name,
                                        url = pokemonModel.url
                                    )
                                })
                        )
                    )
                }
        }
    }
}