package com.example.pokemon.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokemon.data.InternetManager
import com.example.pokemon.data.PokemonApi
import com.example.pokemon.data.entities.Results
import com.example.pokemon.data.room.PokemonDao
import com.example.pokemon.data.room.PokemonEntity
import com.example.pokemon.domane.PokemonModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PokemonDataSource @Inject constructor(
    private val baseSource: BaseSource,
    private val api: PokemonApi,
    private val roomDao: PokemonDao,
    private val internetManager: InternetManager,
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
                if (internetManager.isInternetConnected()) {
                    return try {
                        when (val response =
                            oneShotCalls { api.getPaginationMainPokemon(position * 20) }) {
                            is Results.Success -> {
                                val data = response.data.pokemonList.map { singleItem ->
                                    PokemonModel(
                                        name = singleItem.name, url = singleItem.url
                                    )
                                }
                                withContext(Dispatchers.IO) {
                                    data.forEach {
                                        roomDao.insertPokemonEntity(
                                            PokemonEntity(
                                                name = it.name, url = it.url
                                            )
                                        )
                                    }
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
                } else {
                    return try {
                        val entities =
                            roomDao.getPagedList(params.loadSize, offset = position * 20).map {
                                PokemonModel(
                                    name = it.name, url = it.url
                                )
                            }
                        LoadResult.Page(
                            data = entities,
                            prevKey = if (position == 0) null else position.minus(1),
                            nextKey = if (entities.isEmpty()) null else position.plus(1)
                        )
                    } catch (e: Exception) {
                        LoadResult.Error(e)
                    }
                }
            }
        }
    }
}
