package com.example.pokemon.presentation.adapter

import androidx.recyclerview.widget.DiffUtil

class StartDiffUtil : DiffUtil.ItemCallback<StartModel>() {

    override fun areItemsTheSame(oldItem: StartModel, newItem: StartModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: StartModel, newItem: StartModel): Boolean {
        return oldItem.name == newItem.name
    }
}