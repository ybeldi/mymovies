package com.cic.formation.mymovies.ui.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.cic.formation.mymovies.data.api.json.Movies
import com.cic.formation.mymovies.data.utils.Results
import com.cic.formation.mymovies.domain.GetMoviesUseCase
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
class MoviesListViewModel @ViewModelInject constructor(
    getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {
    private val _showMovies = getMoviesUseCase.invoke(Unit).asLiveData()
    val showMovies: LiveData<Results<List<Movies>>>
        get() = _showMovies

}