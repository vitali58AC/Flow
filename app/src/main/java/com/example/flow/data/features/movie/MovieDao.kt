package com.example.flow.data.features.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flow.data.features.movie.models.Movie
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: List<Movie>): List<Long>

    @Query("SELECT * FROM ${Movie.MOVIE_TABLE_NAME}")
    fun getAllMovie(): Flow<List<Movie>>

    @Query("SELECT * FROM ${Movie.MOVIE_TABLE_NAME} WHERE title LIKE '%' || :name || '%' AND type =:type")
    suspend fun getMovieByNameAndType(name: String, type: String): List<Movie>

    @Query("SELECT * FROM ${Movie.MOVIE_TABLE_NAME} WHERE title LIKE '%' || :name || '%'")
    suspend fun getMovieByName(name: String): List<Movie>

    @Query("SELECT * FROM ${Movie.MOVIE_TABLE_NAME} WHERE type =:type")
    suspend fun getMovieByType(type: String): List<Movie>
}

