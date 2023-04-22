package com.example.pokemon.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {

    @GET("api/v2/pokemon")
    suspend fun getPokemon(): Response<PokemonApiResponse>

    @GET("api/v2/pokemon/{id}")
    suspend fun getPokemonById(@Path(value = "id") id: Int): Response<PokemonApiDetail>
}