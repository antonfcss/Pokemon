package com.example.pokemon.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.pokemon.databinding.RecyclerviewItemPokemonBinding

class StartAdapter(
    private val onClick: (String) -> Unit
) : ListAdapter<StartModel, StartViewHolder>(StartDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StartViewHolder {
        return StartViewHolder(
            RecyclerviewItemPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StartViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }
}