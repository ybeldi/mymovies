package com.cic.formation.mymovies.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cic.formation.mymovies.data.api.json.Movies
import com.cic.formation.mymovies.data.utils.Results
import com.cic.formation.mymovies.domain.GetMovieDetailsUseCase
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@FragmentScoped
class MovieDetailsViewModel @ViewModelInject constructor(
    private val getSectionDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val _showSectionDetails = MutableLiveData<Results<Movies>>()
    val showSectionDetails: LiveData<Results<Movies>>
        get() = _showSectionDetails

    fun getMovieById(id: Int) {
        viewModelScope.launch {
            getSectionDetailsUseCase.invoke(id).collect {
                _showSectionDetails.value = it
            }
        }
    }
}