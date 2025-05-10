package com.khalil.paymobtask.utils

object Constants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMG_URL = "https://image.tmdb.org/t/p/w500"
    const val BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxYTFkMmViNmI4MjZiMWUwNzBmMGVmMTM5YWQ5OGRlMSIsIm5iZiI6MTc0NjcyNzMzNS4xMzIsInN1YiI6IjY4MWNmMWE3ZGM5MzcyODViYzg4YzM5NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.YFQ0x4_Xa9jgHX8ffz3y3OSr5841tj7IOkYnTQJFecQ"
}
// Note: I’m aware that storing the API key like this is not secure.
// It’s recommended to store it in the build configuration (e.g., BuildConfig or Gradle properties).
// However, I'm currently facing an issue with the Gradle setup and working on resolving it.
