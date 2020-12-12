package com.cic.formation.mymovies.data


import com.cic.formation.mymovies.data.api.APIService
import com.cic.formation.mymovies.data.api.json.Movies
import com.cic.formation.mymovies.data.api.json.SectionDetailsResponse
import com.cic.formation.mymovies.data.utils.Results
import com.cic.formation.mymovies.data.utils.handleResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

interface IRemoteDataSource {
    fun fetchAllMovies(): Flow<Results<List<Movies>>>
    fun getMoviesById(id: Int): Flow<Results<Movies>>
}

@Singleton
class MoviesRemoteDataSource @Inject constructor(
    private val remoteService: APIService
) : IRemoteDataSource {

    override fun fetchAllMovies(): Flow<Results<List<Movies>>> = flow {
        emit(Results.Loading)
        try {
            emit(
                remoteService.getMovies().handleResponse(
                    onError = { msg ->
                        Results.Error(IOException(msg))
                    },
                    onSuccess = {
                        Results.Success(it.results)
                    })
            )
        } catch (thr: Throwable) {
            emit(Results.Error(IOException("Error loading remote data, please check your connectivity")))
        }
    }

    override fun getMoviesById(id: Int): Flow<Results<Movies>> = flow {
        emit(Results.Loading)
        try {
            emit(remoteService.getMoviesById(id).handleResponse(
                onSuccess = {
                    Results.Success(it)
                },
                onError = { msg ->
                    Results.Error(IOException(msg))
                }
            ))
        } catch (thr: Throwable) {
            emit(Results.Error(IOException("Error loading remote data, please check your connectivity")))
        }
    }
}
