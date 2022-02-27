package com.myspotify.data.di

import android.content.Context
import androidx.room.Room
import com.myspotify.data.local.AppDatabase
import com.myspotify.data.remote.MySpotifyApi
import com.myspotify.data.repository.SongsRepository
import com.myspotify.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRepository(api :MySpotifyApi, db: AppDatabase) = SongsRepository(api , db)

    @Singleton
    @Provides
    fun provideApi():MySpotifyApi {
        return Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MySpotifyApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext applicationContext: Context):AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "mySpotify.db"
        ).build()
    }
}