package com.cic.formation.mymovies.domain

import com.cic.formation.mymovies.data.MoviesRepository
import com.cic.formation.mymovies.data.api.json.Movies
import com.cic.formation.mymovies.data.utils.Results
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


class GetMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) : UseCase<Unit, List<Movies>>() {
    override fun invoke(parameters: Unit) = moviesRepository.fetchAllMovies().map { result ->
        when (result) {
            is Results.Success -> {
                Results.Success(result.data)
            }
            is Results.Loading -> {
                Results.Loading
            }
            is Results.Error -> {
                Results.Error(IOException(result.exception.message))
            }

        }

    }
}
