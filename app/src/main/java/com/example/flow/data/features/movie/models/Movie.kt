package com.example.flow.data.features.movie.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = Movie.MOVIE_TABLE_NAME)
@JsonClass(generateAdapter = true)
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @Json(name = "Title")
    val title: String? = null,
    @Json(name = "Year")
    val year: String? = null,
    @ColumnInfo(name = IMDB_ID)
    val imdbID: String? = null,
    @Json(name = "Type")
    val type: String? = null,
    @Json(name = "Poster")
    val poster: String? = null
) {
    companion object {
        const val MOVIE_TABLE_NAME = "Movie"
        const val IMDB_ID = "imdb_id"
    }
}

@JsonClass(generateAdapter = true)
data class Root(
    @Json(name = "Search")
    val search: List<Movie>?,
    val totalResults: String?,
    @Json(name = "Response")
    var response: String
)