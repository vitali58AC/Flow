package com.example.flow.data.network

import com.example.flow.data.features.movie.models.Root
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "390c98f7"

interface OmdbApi {

    @GET("/")
    suspend fun searchMovie(
        @Query("apikey") key: String = API_KEY,
        @Query("s") text: String,
        @Query("type") type: String
    ): Root
}