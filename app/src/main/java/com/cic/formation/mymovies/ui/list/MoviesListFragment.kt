package com.cic.formation.mymovies.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.cic.formation.mymovies.R
import com.cic.formation.mymovies.data.utils.Results
import com.cic.formation.mymovies.ui.list.adapter.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movies_list.*

@AndroidEntryPoint
class MoviesListFragment : Fragment() {

    private val moviesViewModel: MoviesListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val moviesAdapter = MoviesAdapter(emptyList()) {
            findNavController().navigate(
                R.id.list_to_detail,
                bundleOf("movieId" to it.id)
            )
        }
        moviesRecyclerView.adapter = moviesAdapter
        moviesViewModel.showMovies.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Results.Loading -> {
                    stateTextView.visibility = View.VISIBLE
                    stateTextView.text = getString(R.string.loading)
                    moviesRecyclerView.visibility = View.GONE
                }
                is Results.Success -> {
                    stateTextView.visibility = View.GONE
                    moviesRecyclerView.visibility = View.VISIBLE
                    moviesAdapter.updateMovies(result.data)
                }
                is Results.Error -> {
                    stateTextView.visibility = View.VISIBLE
                    stateTextView.text = result.exception.message
                    moviesRecyclerView.visibility = View.GONE
                }
            }
        })
    }
}