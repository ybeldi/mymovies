package com.cic.formation.mymovies

import com.cic.formation.mymovies.data.MoviesLocalDataSource
import com.cic.formation.mymovies.data.MoviesRemoteDataSource
import com.cic.formation.mymovies.data.MoviesRepository
import com.cic.formation.mymovies.data.api.json.Movies
import com.cic.formation.mymovies.data.utils.Results
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException

class MoviesRepositoryTest {


    private val localDataSource: MoviesLocalDataSource = mock()
    private val remoteDataSource: MoviesRemoteDataSource = mock()
    private val moviesRepository = MoviesRepository(localDataSource, remoteDataSource)

    private val remoteTestMovies = listOf(
        Movies(
            id = 1,
            title = "Film 1",
            overview = "overview 1",
            release_date = "01/01/2020",
            poster_path = "poster1",
            vote_average = 5.0
        ),
        Movies(
            id = 2,
            title = "Film 2",
            overview = "overview 2",
            release_date = "01/01/2020",
            poster_path = "poster2",
            vote_average = 5.0
        ),
        Movies(
            id = 3,
            title = "Film 3",
            overview = "overview 3",
            release_date = "01/01/2020",
            poster_path = "poster3",
            vote_average = 5.0
        )
    )

    private val localTestMovies = listOf(
        Movies(
            id = 1,
            title = "Film 1",
            overview = "overview 1",
            release_date = "01/01/2020",
            poster_path = "poster1",
            vote_average = 5.0
        ),
        Movies(
            id = 2,
            title = "Film 2",
            overview = "overview 2",
            release_date = "01/01/2020",
            poster_path = "poster2",
            vote_average = 5.0
        ),
        Movies(
            id = 3,
            title = "Film 3",
            overview = "overview 3",
            release_date = "01/01/2020",
            poster_path = "poster3",
            vote_average = 5.0
        )
    )

    /**
     * Check if the loading status is emitted
     * We will consume only the first emitted value, since the Loading is always emitted first
     */
    @Test
    fun loadMovies_withLoading() = runBlocking {

        // Given
        val loadingResult = Results.Loading
        whenever(localDataSource.fetchAllMovies()).thenReturn(flow { emit(emptyList<Movies>()) })
        whenever(remoteDataSource.fetchAllMovies()).thenReturn(flow { emit(loadingResult) })

        // When
        val result = moviesRepository.fetchAllMovies().first()

        // Expected Result
        assertEquals(result, loadingResult)

    }

    /**
     * Check is the error status is emitted
     * We will listen to the collected values until the error is emitted
     */
    @Test
    fun loadMovies_withError() = runBlocking {

        // Given
        val errorResult = Results.Error(IOException(""))
        var collectedResult: Results<List<Movies>>? = null
        whenever(localDataSource.fetchAllMovies()).thenReturn(flow { emit(emptyList<Movies>()) })
        whenever(remoteDataSource.fetchAllMovies()).thenReturn(flow { emit(errorResult) })

        // When
        moviesRepository.fetchAllMovies().collect {
            collectedResult = it
        }


        // Expected Result
        assertEquals(collectedResult, errorResult)

    }

    /**
     * Check is the movies are successfully loaded locally
     */
    @Test
    fun loadMoviessLocally_withSuccess() = runBlocking {

        // Given
        val successResults = Results.Success(localTestMovies)
        var collectedResult: Results<List<Movies>>? = null
        whenever(localDataSource.fetchAllMovies()).thenReturn(flow { emit(localTestMovies) })
        whenever(remoteDataSource.fetchAllMovies()).thenReturn(flow {
            emit(Results.Success(emptyList<Movies>()))
        })

        // When
        moviesRepository.fetchAllMovies().collect {
            collectedResult = it
        }

        // Expected Result
        assertEquals(collectedResult, successResults)
    }

    /**
     * Check is the sections are successfully loaded remotely
     */
    @Test
    fun loadMoviesRemotely_withSuccess() = runBlocking {

        // Given
        val successResults = Results.Success(remoteTestMovies)
        var collectedResult: Results<List<Movies>>? = null
        whenever(localDataSource.fetchAllMovies()).thenReturn(flow { emit(emptyList<Movies>()) })
        whenever(remoteDataSource.fetchAllMovies()).thenReturn(flow { emit(successResults) })

        // When
        moviesRepository.fetchAllMovies().collect {
            collectedResult = it
        }

        // Expected Result
        assert(collectedResult is Results.Success)
    }


}
