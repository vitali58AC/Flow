package com.example.flow.data.features.movie


import com.example.flow.data.database.Database
import com.example.flow.data.features.movie.models.Movie
import com.example.flow.data.features.movie.models.MovieType
import com.example.flow.data.features.movie.models.Root
import com.example.flow.data.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class SearchMovieRepository {

    private val movieDao = Database.instance.movieDao()


    suspend fun saveMovies(movies: List<Movie>) {
        movieDao.insertMovie(movies)
    }

    fun observeMovies() = movieDao.getAllMovie()

    suspend fun searchMovie(name: String, type: MovieType): Root {
        return withContext(Dispatchers.IO) {
            try {
                Network.omdbApi.searchMovie(text = name.trim(), type = type.type)
            } catch (t: Throwable) {
                Root(
                    search = searchDatabaseMovie(name.trim(), type),
                    response = "true",
                    totalResults = null
                )
            }
        }
    }

    private suspend fun searchDatabaseMovie(name: String, type: MovieType): List<Movie> {
        Timber.e("Start local search")
        return when {
            name.isNotBlank() && type != MovieType.NOT_SELECTED -> {
                movieDao.getMovieByNameAndType(name, type.type)
            }
            name.isNotBlank() && type == MovieType.NOT_SELECTED -> {
                movieDao.getMovieByName(name)
            }
            name.isBlank() && type != MovieType.NOT_SELECTED -> {
                movieDao.getMovieByType(type.type)
            }
            else -> emptyList()
        }
    }


}