package com.cic.formation.mymovies.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.cic.formation.mymovies.R
import com.cic.formation.mymovies.data.api.json.Movies
import com.cic.formation.mymovies.data.utils.Results
import com.cic.formation.mymovies.utils.loadImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_details.*

const val ARG_SELECTED_MOVIE = "ARG_SELECTED_MOVIE"

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    lateinit var movie: Movies

    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = arguments?.getInt("movieId")
        viewModel.getMovieById(movieId!!)

        viewModel.showSectionDetails.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Results.Loading -> {

                }
                is Results.Success -> {
                    movie = result.data
                    imagePoster.loadImage(movie!!.poster_path)
                    //title.text = movie.title
                    movieTitle.text = movie.title
                    releaseDate.text = getString(R.string.release_date, movie.release_date)
                    overview.text = movie.overview
                }
                is Results.Error -> {

                }
            }
        })
    }

}
