package com.example.pokemon.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.databinding.RecyclerviewItemPokemonBinding
import com.example.pokemon.domane.PokemonModel

class StartViewHolder(
    private val binding: RecyclerviewItemPokemonBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(startModel: PokemonModel, clickListener: (String) -> Unit) {
        binding.pokemonName.text = startModel.name.replaceFirstChar(Character::toUpperCase)
        binding.pokemonNumberTextView.text = (adapterPosition + 1).toString()
        binding.root.setOnClickListener { clickListener.invoke(startModel.url) }
    }

}