package com.khalil.paymobtask.data.repositoriesimp

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.khalil.paymobtask.data.datasource.models.Movie
import kotlinx.coroutines.flow.first

class MoviePagingSource(
    private val movieRepository: MovieRepository
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = movieRepository.getDiscoverMovies(page = page)

            val favoriteMovies = try {
                movieRepository.getFavoriteMovies().first()
            } catch (e: Exception) {
                Log.e("PagingError", "Error getting favorites", e)
                emptyList()
            }
            val favoriteIds = favoriteMovies.map { it.id }.toSet()

            val moviesWithFavorites = response.results.map { apiMovie ->
                apiMovie.copy(isFavorite = favoriteIds.contains(apiMovie.id))
            }
            LoadResult.Page(
                data = moviesWithFavorites,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page >= response.total_pages) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
