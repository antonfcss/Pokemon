package com.example.pokemon.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("pokemon")
class PokemonEntity(
    @PrimaryKey
    val name: String,
    val url: String
)
