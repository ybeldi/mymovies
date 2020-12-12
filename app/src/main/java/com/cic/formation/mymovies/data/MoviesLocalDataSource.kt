package com.cic.formation.mymovies.data

import com.cic.formation.mymovies.data.api.json.Movies
import com.cic.formation.mymovies.data.database.MoviesDao
import kotlinx.coroutines.flow.Flow

interface ISectionLocalDataSource {
    fun fetchAllMovies(): Flow<List<Movies>>
    fun getMovieById(movieId: Int): Flow<Movies>
    suspend fun insertAll(sections: List<Movies>)
    suspend fun update(section: Movies)
}

class MoviesLocalDataSource(private val moviesDao: MoviesDao) : ISectionLocalDataSource {

    override fun fetchAllMovies() = moviesDao.fetchMovies()
    override fun getMovieById(movieId: Int) = moviesDao.getMovieById(movieId)

    override suspend fun insertAll(sections: List<Movies>) {
        moviesDao.insertAll(sections)
    }

    override suspend fun update(section: Movies) {
        moviesDao.update(section)
    }
}