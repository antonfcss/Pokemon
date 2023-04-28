package com.example.pokemon.presentation.aboutPokemon

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.pokemon.MainActivity
import com.example.pokemon.R
import com.example.pokemon.base.BaseFragment
import com.example.pokemon.base.ViewState
import com.example.pokemon.databinding.AboutPokemonFragmentBinding
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
        with(binding) {
            pokemonImageView.isVisible = true
            constraintInternal.isVisible = true
            pokemonNameTextView.isVisible = true
            pokemonWeightTextView.isVisible = true
            pokemonHeightTextView.isVisible = true
            errorLayout.root.isVisible = false
            progress.isVisible = false
        }
        binding.pokemonNameTextView.text =
            viewState.data.pokemonModelDetail.name
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

    override fun renderErrorState(viewState: ViewState.Error) {
        with(binding) {
            errorLayout.root.isVisible = true
            errorLayout.retry.setOnClickListener {
                viewModel.getPokemonDetail(arguments?.getString("url")!!)
            }
            errorLayout.textError.text = viewState.message
            pokemonImageView.isVisible = false
            progress.isVisible = false
            constraintInternal.isVisible = false
            pokemonNameTextView.isVisible = false
            pokemonWeightTextView.isVisible = false
            pokemonHeightTextView.isVisible = false
        }
    }

    override fun renderLoadingState(viewState: ViewState.Loading) {
        with(binding) {
            progress.isVisible = true
            errorLayout.root.isVisible = false
            pokemonImageView.isVisible = false
            constraintInternal.isVisible = false
            pokemonNameTextView.isVisible = false
            pokemonWeightTextView.isVisible = false
            pokemonHeightTextView.isVisible = false
        }
    }
}
