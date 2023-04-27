package com.example.pokemon.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.pokemon.R
import com.example.pokemon.databinding.StartFragmentBinding
import com.example.pokemon.presentation.adapter.StartAdapter
import com.example.pokemon.presentation.adapter.StartLoaderStateAdapter
import com.pult.application.base.BaseFragment
import com.pult.application.base.ViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StartFragment : BaseFragment<StartFragmentBinding, StartViewModel, StartState>() {
    private val startAdapter: StartAdapter by lazy {
        StartAdapter() {
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
        startAdapter.addLoadStateListener { state: CombinedLoadStates ->
            with(binding) {
                recyclerViewPokemon.isVisible = state.refresh != LoadState.Loading
                progress.isVisible = state.refresh == LoadState.Loading
            }
        }
        viewModel.getPokemonList()
    }

    override fun renderSuccessState(viewState: ViewState.Success<StartState>) {
        CoroutineScope(Dispatchers.Main).launch {
            startAdapter.submitData(viewState.data.startModelList)
        }
        Log.d("StartFragment", viewState.toString())
    }
}