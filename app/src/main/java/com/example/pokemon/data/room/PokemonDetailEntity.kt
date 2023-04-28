package com.example.pokemon.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("aboutPokemon")
data class PokemonDetailEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Double,
    val frontImage: String,
    val type: String
)
