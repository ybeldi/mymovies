package com.cic.formation.mymovies.data

import com.cic.formation.mymovies.data.api.json.Movies
import com.cic.formation.mymovies.data.utils.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

interface IMoviesRepository {
    fun fetchAllMovies(): Flow<Results<List<Movies>>>
    fun getMovieById(id: Int): Flow<Results<Movies>>
}

@Singleton
class MoviesRepository @Inject constructor(
    private val localDataSource: MoviesLocalDataSource,
    private val remoteDataSource: MoviesRemoteDataSource
) : IMoviesRepository {

    override fun fetchAllMovies(): Flow<Results<List<Movies>>> =
        flow {
            emit(Results.Loading)
            localDataSource.fetchAllMovies()
                .collect {
                    if (it.isNullOrEmpty()) {
                        remoteDataSource.fetchAllMovies().collect { remoteResponse ->
                            when (remoteResponse) {
                                is Results.Success -> {
                                    val movies = remoteResponse.data
                                    localDataSource.insertAll(movies)
                                    emit(Results.Success(movies))
                                }
                                is Results.Error -> emit(Results.Error(remoteResponse.exception))
                                else -> emit(Results.Loading)
                            }
                        }
                    } else {
                        emit(Results.Success(it))
                    }
                }
        }.flowOn(Dispatchers.IO)

    override fun getMovieById(id: Int) = flow {
        emit(Results.Loading)
        localDataSource.getMovieById(id).collect {
            if (it.title == null) {
                remoteDataSource.getMoviesById(id)
                    .collect { remoteResponse ->
                        when (remoteResponse) {
                            is Results.Success -> {
                                remoteResponse.data
                                localDataSource.update(remoteResponse.data)
                                emit(Results.Success(remoteResponse.data))
                            }
                            is Results.Error -> emit(Results.Error(remoteResponse.exception))
                            else -> emit(Results.Loading)
                        }
                    }
            } else {
                emit(Results.Success(it))
            }
        }
    }.flowOn(Dispatchers.IO)
}