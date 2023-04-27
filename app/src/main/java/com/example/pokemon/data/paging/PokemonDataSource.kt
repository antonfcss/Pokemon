package com.example.pokemon.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokemon.data.PokemonApi
import com.example.pokemon.data.entities.Results
import com.example.pokemon.domane.PokemonModel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PokemonDataSource @Inject constructor(
    private val baseSource: BaseSource,
    private val api: PokemonApi,
) : BaseSource by baseSource {

    fun getPagingPokemon(): PagingSource<Int, PokemonModel> {
        return object : PagingSource<Int, PokemonModel>() {
            override fun getRefreshKey(state: PagingState<Int, PokemonModel>): Int? {
                return state.anchorPosition?.let { anchorPosition ->
                    state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                        ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
                }
            }

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonModel> {
                val position = params.key ?: 0
                return try {
                    when (val response =
                        oneShotCalls { api.getPaginationMainPokemon(position * 20) }) {
                        is Results.Success -> {
                            val data = response.data.pokemonList.map { singleItem ->
                                PokemonModel(
                                    name = singleItem.name, url = singleItem.url
                                )
                            }
                            if (data.isNullOrEmpty()) {
                                LoadResult.Error(Throwable("Empty data"))
                            } else {
                                LoadResult.Page(
                                    data = data,
                                    prevKey = if (position == 0) null else position - 1,
                                    nextKey = if (response.data.pokemonList.isEmpty()) null else position + 1
                                )
                            }

                        }

                        is Results.Error -> {
                            LoadResult.Error(response.exception)
                        }
                    }
                } catch (exception: IOException) {
                    LoadResult.Error(exception)
                } catch (exception: HttpException) {
                    LoadResult.Error(exception)
                }
            }
        }
    }
}
