package com.example.pokemon.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.pokemon.data.paging.PokemonDataSource
import com.example.pokemon.data.room.PathResolver
import com.example.pokemon.data.room.PokemonDao
import com.example.pokemon.data.room.PokemonDetailEntity
import com.example.pokemon.domane.PokemonModel
import com.example.pokemon.domane.PokemonModelDetail
import com.example.pokemon.domane.PokemonType
import com.example.pokemon.domane.SpritesModel
import com.example.pokemon.domane.exceptions.NoDataException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject


class PokemonRepository @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val context: Context,
    private val pokemonDataSource: PokemonDataSource,
    private val roomDao: PokemonDao,
    private val internetManager: InternetManager,
    private val pathResolver: PathResolver,
    private val scope: CoroutineScope
) {

    fun getPagingPokemon(): Flow<PagingData<PokemonModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { pokemonDataSource.getPagingPokemon() }
        ).flow.cachedIn(scope)
    }

    suspend fun getDetailPokemon(id: Int): Flow<PokemonModelDetail> {
        return flow {
            if (internetManager.isInternetConnected()) {
                val apiModel = pokemonApi.getPokemonById(id).body()!!
                val bitmap = getImageFromRemote(
                    context,
                    apiModel.spritesApiModel.frontImage
                )
                val types = apiModel.types.map { apiType ->
                    PokemonType(
                        type = apiType.type.typeName
                    )
                }

                bitmap?.let { notNullBitmap ->
                    saveImageToInternalStorage(
                        name = apiModel.name,
                        height = apiModel.height,
                        weight = apiModel.weight.toDouble(),
                        frontImage = notNullBitmap,
                        type = types.joinToString(
                            separator = ", ",
                            transform = { it.type },
                            postfix = "."
                        ),
                        id = id,
                    )
                }

                emit(
                    PokemonModelDetail(
                        name = apiModel.name.replaceFirstChar(Character::toUpperCase),
                        height = apiModel.height,
                        weight = apiModel.weight.toDouble(),
                        spritesApiModel = SpritesModel(
                            frontImage = bitmap
                        ),
                        types = types
                    )
                )
            } else {
                val dbModel = roomDao.getPokemonDetails(id)
                Log.d("DATABASE", "dbModel: $dbModel")
                if (dbModel != null) {
                    val bitmap = loadImageFromStorage(id)
                    if (bitmap != null) {
                        emit(
                            PokemonModelDetail(
                                name = dbModel.name,
                                height = dbModel.height,
                                weight = dbModel.weight,
                                spritesApiModel = SpritesModel(
                                    frontImage = bitmap
                                ),
                                types = listOf(PokemonType(dbModel.type))
                            )
                        )
                    }
                } else {
                    throw NoDataException()
                }
            }
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

    private suspend fun saveImageToInternalStorage(
        name: String,
        height: Int,
        weight: Double,
        frontImage: Bitmap,
        type: String,
        id: Int
    ) {
        saveToInternalStorage(frontImage, id)?.let { imagePath ->
            roomDao.insertAboutPokemon(
                PokemonDetailEntity(
                    id = id,
                    name = name,
                    height = height,
                    weight = weight,
                    frontImage = imagePath,
                    type = type,
                )
            )
        }
    }

    private fun saveToInternalStorage(bitmapImage: Bitmap, id: Int): String? {
        val directory: File = context.getDir("imageDir", Context.MODE_PRIVATE)
        val mypath = File(directory, "$id.jpg")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(mypath)
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return directory.absolutePath
    }

    private fun loadImageFromStorage(id: Int): Bitmap? {
        return try {
            val file = File(pathResolver.getPathById(id))
            BitmapFactory.decodeStream(FileInputStream(file))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            null
        }
    }

}
