package com.cic.formation.mymovies.ui.list.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cic.formation.mymovies.R
import com.cic.formation.mymovies.data.api.json.Movies
import com.cic.formation.mymovies.utils.inflate
import com.cic.formation.mymovies.utils.loadImage
import kotlinx.android.synthetic.main.item_movie.view.*

/*
 *
 * MoviesAdapter
 * My movies
 *
 * Created by Yassine BELDI @Y4583L on 3/1/20.
 * Copyright © 2020 IBM. All rights reserved.
 *
 * This file may not be copied and/or distributed without the express
 * permission of IBM.
 *
 */

class MoviesAdapter(
    var movies: List<Movies>,
    val listener: ((Movies) -> Unit)
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {


    fun updateMovies(newMovies: List<Movies>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_movie)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = movies[position]
        item.let { holder.bind(it, listener) }
    }

    override fun getItemCount(): Int = movies.size

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        fun bind(item: Movies, listener: ((Movies) -> Unit)) = with(itemView) {
            movieTitle.text = item.title
            movieImage.loadImage(item.poster_path)
            releaseDate.text = item.release_date
            overview.text = item.overview
            setOnClickListener { listener(item) }
        }
    }
}