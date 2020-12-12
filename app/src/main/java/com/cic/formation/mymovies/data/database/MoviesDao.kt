package com.cic.formation.mymovies.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cic.formation.mymovies.data.api.json.Movies
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies")
    fun fetchMovies(): Flow<List<Movies>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sections: List<Movies>)

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieById(id: Int): Flow<Movies>

    @Update
    suspend fun update(movies: Movies)
}