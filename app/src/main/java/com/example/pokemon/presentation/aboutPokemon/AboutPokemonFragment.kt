package com.example.pokemon.presentation.aboutPokemon

import android.os.Bundle
import android.view.View
import com.example.pokemon.MainActivity
import com.example.pokemon.R
import com.example.pokemon.databinding.AboutPokemonFragmentBinding
import com.pult.application.base.BaseFragment
import com.pult.application.base.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutPokemonFragment :
    BaseFragment<AboutPokemonFragmentBinding, AboutPokemonViewModel, AboutPokemonState>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPokemonDetail(arguments?.getString("url")!!)
        (requireActivity() as MainActivity).isBackButtonEnable(true)
    }


    override fun renderSuccessState(viewState: ViewState.Success<AboutPokemonState>) {
        binding.pokemonNameTextView.text =
            viewState.data.pokemonModelDetail.name.replaceFirstChar(Character::toUpperCase)
        binding.pokemonHeightTextView.text =
            getString(R.string.pokemon_height, viewState.data.pokemonModelDetail.height.toString())
        binding.pokemonWeightTextView.text =
            getString(R.string.pokemon_weight, viewState.data.pokemonModelDetail.weight.toString())
        binding.pokemonTypeTextView.text = getString(
            R.string.pokemon_type, viewState.data.pokemonModelDetail.types.joinToString(
                separator = ", ",
                transform = { it.type },
                postfix = "."
            )
        )
        binding.pokemonImageView.setImageBitmap(viewState.data.pokemonModelDetail.spritesApiModel.frontImage)
    }
}