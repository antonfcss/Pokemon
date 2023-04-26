package com.example.pokemon.presentation.aboutPokemon

import android.util.Log
import com.example.pokemon.domane.GetPokemonDetailUseCase
import com.pult.application.base.BaseViewModel
import com.pult.application.base.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class AboutPokemonViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase
) : BaseViewModel<AboutPokemonState>() {

    fun getPokemonDetail(url: String) {
        launchIO {
            getPokemonDetailUseCase.getPokemonDetail(url)
                .catch { Log.d("AboutPokemonViewModel", it.message.toString()) }
                .collect {
                    updateState(ViewState.Success(AboutPokemonState(it)))
                    Log.d("AboutPokemonViewModel", it.toString())
                }
        }

    }
}