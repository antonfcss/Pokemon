package com.example.pokemon.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.pokemon.databinding.RecyclerviewItemPokemonBinding
import com.example.pokemon.domane.PokemonModel

class StartAdapter(
    private val onClick: (String) -> Unit
) : PagingDataAdapter<PokemonModel, StartViewHolder>(StartDiffUtil()) {

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
        getItem(position)?.let { holder.bind(it, onClick) }
    }
}