package com.example.pokemon.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.pokemon.domane.PokemonModel

class StartDiffUtil : DiffUtil.ItemCallback<PokemonModel>() {

    override fun areItemsTheSame(oldItem: PokemonModel, newItem: PokemonModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PokemonModel, newItem: PokemonModel): Boolean {
        return oldItem.name == newItem.name
    }

}
