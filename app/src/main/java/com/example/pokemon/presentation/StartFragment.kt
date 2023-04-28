package com.example.pokemon.presentation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.pokemon.R
import com.example.pokemon.base.BaseFragment
import com.example.pokemon.base.ViewState
import com.example.pokemon.databinding.StartFragmentBinding
import com.example.pokemon.presentation.adapter.StartAdapter
import com.example.pokemon.presentation.adapter.StartLoaderStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StartFragment : BaseFragment<StartFragmentBinding, StartViewModel, StartState>() {
    private val startAdapter: StartAdapter by lazy {
        StartAdapter {
            findNavController().navigate(
                R.id.action_startFragment_to_aboutPokemonFragment, bundleOf("url" to it)
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            recyclerViewPokemon.adapter = startAdapter
            recyclerViewPokemon.adapter =
                startAdapter.withLoadStateFooter(StartLoaderStateAdapter())
        }
        viewModel.getPokemonList()
    }

    override fun renderSuccessState(viewState: ViewState.Success<StartState>) {
        with(binding) {
            recyclerViewPokemon.isVisible = true
            errorLayout.root.isVisible = false
            progress.isVisible = false
        }
        CoroutineScope(Dispatchers.Main).launch {
            startAdapter.submitData(viewState.data.startModelList)
        }
    }

    override fun renderErrorState(viewState: ViewState.Error) {
        with(binding) {
            progress.isVisible = false
            errorLayout.root.isVisible = true
            errorLayout.retry.isEnabled = true
            recyclerViewPokemon.isVisible = false
            errorLayout.retry.setOnClickListener {
                viewModel.getPokemonList()
                it.isEnabled = false
            }
        }
    }

    override fun renderLoadingState(viewState: ViewState.Loading) {
        with(binding) {
            recyclerViewPokemon.isVisible = false
            progress.isVisible = true
        }
    }
}
