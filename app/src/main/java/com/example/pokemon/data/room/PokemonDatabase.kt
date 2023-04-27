package com.example.pokemon.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PokemonEntity::class, AboutPokemonEntity::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun dao(): PokemonDao

}
