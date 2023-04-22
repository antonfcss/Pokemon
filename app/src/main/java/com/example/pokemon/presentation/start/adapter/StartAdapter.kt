package com.example.pokemon.presentation.start.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class StartAdapter(
    private val clickListener: (StartModel) -> Unit
) : ListAdapter<StartModel, StartViewHolder>(StartDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StartViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: StartViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}