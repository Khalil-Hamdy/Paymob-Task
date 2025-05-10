package com.khalil.paymobtask.data.datasource.remote

import com.khalil.paymobtask.data.datasource.models.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService{
    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): MovieResponse
}