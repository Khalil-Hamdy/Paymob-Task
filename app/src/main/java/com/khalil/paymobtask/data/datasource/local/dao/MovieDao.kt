package com.khalil.paymobtask.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khalil.paymobtask.data.datasource.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM favorites")
    fun getFavoriteMovies(): Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToFavorites(movie: Movie)

    @Delete
    fun removeFromFavorites(movie: Movie)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :movieId)")
     fun isFavorite(movieId: Int): Boolean
}