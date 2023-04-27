package com.example.pokemon.data

import com.example.pokemon.data.entities.PokemonApiDetail
import com.example.pokemon.data.entities.PokemonApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("api/v2/pokemon")
    suspend fun getPaginationMainPokemon(@Query("offset") offset: Int): Response<PokemonApiResponse>

    @GET("api/v2/pokemon/{id}")
    suspend fun getPokemonById(@Path(value = "id") id: Int): Response<PokemonApiDetail>
}