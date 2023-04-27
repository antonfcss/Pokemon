package com.example.pokemon.di

import com.example.pokemon.data.paging.BaseSource
import com.example.pokemon.data.paging.BaseSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BaseSourceModule {

    @Binds
    fun bindsBaseSource(source: BaseSourceImpl): BaseSource

}
