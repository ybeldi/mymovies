package com.cic.formation.mymovies.data.api

import com.cic.formation.mymovies.BuildConfig
import com.cic.formation.mymovies.data.api.json.SectionDetailsResponse
import com.cic.formation.mymovies.data.api.json.JsonResponse
import com.cic.formation.mymovies.data.api.json.Movies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface APIService {

    companion object {
        const val API_BASE_URL = BuildConfig.TMDB_BASE_URL
    }

    @GET("3/discover/movie?api_key=${BuildConfig.TMDB_API_KEY}&vote_count.gte=500&language=en&sort_by=release_date.desc/")
    suspend fun getMovies(): Response<JsonResponse>

    @GET("androiddash-se/{id}")
    suspend fun getMoviesById(@Path("id") id: Int): Response<Movies>
}