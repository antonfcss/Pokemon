package com.example.pokemon.data.paging

import com.example.pokemon.data.entities.Results
import retrofit2.Response

interface BaseSource {
    suspend fun <T> oneShotCalls(call: suspend () -> Response<T>): Results<T>
}
