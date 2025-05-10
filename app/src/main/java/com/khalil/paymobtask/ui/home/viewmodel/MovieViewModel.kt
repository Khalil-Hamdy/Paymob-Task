package com.khalil.paymobtask.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.khalil.paymobtask.data.datasource.models.Movie
import com.khalil.paymobtask.data.repositoriesimp.MovieRepository
import com.khalil.paymobtask.ui.home.models.UiMovie
import com.khalil.paymobtask.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.first


@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movieFavList = MutableStateFlow<ApiState<List<Movie>>>(ApiState.Loading)
    val movieFavList: StateFlow<ApiState<List<Movie>>> = _movieFavList

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> get() = _isFavorite

    val moviePagingData: Flow<PagingData<Movie>> = Pager(
        PagingConfig(pageSize = 20, enablePlaceholders = false)
    ) {
        movieRepository.getMoviePagingSource()
    }.flow.cachedIn(viewModelScope)


    fun fetchFavMovies(page: Int) {
        viewModelScope.launch {
            _movieFavList.value = ApiState.Loading
            try {
                val favoriteMovies = movieRepository.getFavoriteMovies().first()
                _movieFavList.value = ApiState.Success(favoriteMovies)
            } catch (e: Exception) {
                _movieFavList.value = ApiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            if (movie.isFavorite) {
                movieRepository.removeFavorite(movie)
                _isFavorite.value = false
            } else {
                movieRepository.addFavorite(movie)
                _isFavorite.value = true
            }
        }

    }

    fun checkIfMovieIsFavorite(movieId: Int) {
        viewModelScope.launch {
            val favoriteStatus = movieRepository.isMovieFavorite(movieId)
            _isFavorite.value = favoriteStatus
        }
    }

}
