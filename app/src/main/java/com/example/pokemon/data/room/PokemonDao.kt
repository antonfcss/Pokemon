package com.example.pokemon.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonEntity(pokemonEntity: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAboutPokemon(pokemonDetailEntity: PokemonDetailEntity)

    @Query("SELECT * FROM pokemon ORDER BY name ASC LIMIT :limit OFFSET :offset")
    suspend fun getPagedList(limit: Int, offset: Int): List<PokemonEntity>

    @Query("SELECT * FROM aboutPokemon WHERE id = :id LIMIT 1")
    suspend fun getPokemonDetails(id: Int): PokemonDetailEntity?

}
