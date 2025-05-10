package com.khalil.paymobtask.data.datasource.local.database
import androidx.room.Database
import androidx.room.RoomDatabase
import com.khalil.paymobtask.data.datasource.local.dao.MovieDao
import com.khalil.paymobtask.data.datasource.models.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}