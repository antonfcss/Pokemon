package com.example.pokemon.di


import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.pokemon.data.room.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAppContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        PokemonDatabase::class.java,
        "pokemon_db"
    ).build()

    @Singleton
    @Provides
    fun provideYourDao(db: PokemonDatabase) = db.dao()

}
