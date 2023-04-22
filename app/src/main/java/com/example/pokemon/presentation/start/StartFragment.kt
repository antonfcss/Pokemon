package com.example.pokemon.presentation

import android.os.Bundle
import android.view.View
import com.example.pokemon.databinding.StartFragmentBinding
import com.example.pokemon.presentation.start.StartState
import com.example.pokemon.presentation.start.StartViewModel
import com.pult.application.base.BaseFragment
import com.pult.application.base.ViewState

class StartFragment: BaseFragment<StartFragmentBinding, StartViewModel, StartState>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun renderSuccessState(viewState: ViewState.Success<StartState>) {
        TODO("Not yet implemented")
    }
}