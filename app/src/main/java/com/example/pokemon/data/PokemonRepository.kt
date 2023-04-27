package com.example.pokemon.data

import android.content.Context
import android.graphics.Bitmap
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.pokemon.data.paging.PokemonDataSource
import com.example.pokemon.domane.PokemonModel
import com.example.pokemon.domane.PokemonModelDetail
import com.example.pokemon.domane.PokemonType
import com.example.pokemon.domane.SpritesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val context: Context,
    private val pokemonDataSource: PokemonDataSource
) {

    fun getPagingPokemon(): Flow<PagingData<PokemonModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { pokemonDataSource.getPagingPokemon() }
        ).flow
    }

    suspend fun getDetailPokemon(id: Int): Flow<PokemonModelDetail> {
        return flow {
            val apiModel = pokemonApi.getPokemonById(id).body()!!
            emit(
                PokemonModelDetail(
                    name = apiModel.name,
                    height = apiModel.height,
                    weight = apiModel.weight.toDouble(),
                    spritesApiModel = SpritesModel(
                        frontImage = getImageFromRemote(
                            context,
                            apiModel.spritesApiModel.frontImage
                        )
                    ),
                    types = apiModel.types.map { apiType ->
                        PokemonType(
                            type = apiType.type.typeName
                        )
                    }
                )
            )
        }
    }

    private suspend fun getImageFromRemote(context: Context, frontImage: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val requestOptions = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                val bitmap = Glide.with(context)
                    .applyDefaultRequestOptions(requestOptions)
                    .asBitmap()
                    .load(frontImage)
                    .submit()
                    .get()
                bitmap
            } catch (e: Exception) {
                null
            }
        }
    }
}