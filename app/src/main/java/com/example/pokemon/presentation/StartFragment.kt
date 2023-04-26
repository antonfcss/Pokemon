package com.example.pokemon.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.pokemon.R
import com.example.pokemon.databinding.StartFragmentBinding
import com.example.pokemon.presentation.adapter.StartAdapter
import com.pult.application.base.BaseFragment
import com.pult.application.base.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment: BaseFragment<StartFragmentBinding, StartViewModel, StartState>() {
    private val startAdapter: StartAdapter by lazy {
        StartAdapter() {
            findNavController().navigate(
                R.id.action_startFragment_to_aboutPokemonFragment,
                bundleOf("url" to it)
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewPokemon.adapter = startAdapter
        viewModel.getPokemonList()
    }

    override fun renderSuccessState(viewState: ViewState.Success<StartState>) {
        startAdapter.submitList(viewState.data.startModelList)
        Log.d("StartFragment", viewState.toString())
    }
}