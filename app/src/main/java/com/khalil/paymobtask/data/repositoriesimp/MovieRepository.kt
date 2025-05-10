package com.khalil.paymobtask.data.repositoriesimp

import com.khalil.paymobtask.data.datasource.models.Movie
import com.khalil.paymobtask.data.datasource.models.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getDiscoverMovies(page: Int): MovieResponse
    suspend fun addFavorite(movie: Movie)
    suspend fun removeFavorite(movie: Movie)
    suspend fun isMovieFavorite(movieId: Int): Boolean
    suspend fun getFavoriteMovies(): Flow<List<Movie>>
    fun getMoviePagingSource(): MoviePagingSource
}