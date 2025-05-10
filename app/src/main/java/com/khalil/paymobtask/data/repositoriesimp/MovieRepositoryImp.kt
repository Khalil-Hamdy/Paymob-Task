package com.khalil.paymobtask.data.repositoriesimp

import com.khalil.paymobtask.data.datasource.local.dao.MovieDao
import com.khalil.paymobtask.data.datasource.models.Movie
import com.khalil.paymobtask.data.datasource.models.MovieResponse
import com.khalil.paymobtask.data.datasource.remote.MovieApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val apiService: MovieApiService,
    private val movieDao: MovieDao
) : MovieRepository {

    override suspend fun getDiscoverMovies(page: Int): MovieResponse {
        return apiService.getDiscoverMovies(page = page)
    }

    override suspend fun addFavorite(movie: Movie) {
        movie.isFavorite = true
        movieDao.addToFavorites(movie)
    }

    override suspend fun removeFavorite(movie: Movie) {
        movie.isFavorite = false
        movieDao.removeFromFavorites(movie)
    }

    override suspend fun isMovieFavorite(movieId: Int): Boolean {
        return movieDao.isFavorite(movieId)
    }

    override suspend fun getFavoriteMovies(): Flow<List<Movie>> {
        return movieDao.getFavoriteMovies()
    }

    // Paging source
    override fun getMoviePagingSource(): MoviePagingSource {
        return MoviePagingSource(this)
    }
}