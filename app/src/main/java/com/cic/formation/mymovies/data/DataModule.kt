package com.cic.formation.mymovies.data

import android.content.Context
import androidx.room.Room
import com.cic.formation.mymovies.data.api.APIService
import com.cic.formation.mymovies.data.database.AppDatabase
import com.cic.formation.mymovies.data.database.MoviesDao
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun provideRetrofit() = Retrofit.Builder()
        .baseUrl(APIService.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideMoviesDao(database: AppDatabase) = database.moviesDao()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(APIService::class.java)

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: APIService) = MoviesRemoteDataSource(apiService)

    @Provides
    @Singleton
    fun provideLocalDataSource(moviesDao: MoviesDao) = MoviesLocalDataSource(moviesDao)
}