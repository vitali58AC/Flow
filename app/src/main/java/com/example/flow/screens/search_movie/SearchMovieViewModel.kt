package com.example.flow.screens.search_movie

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flow.base.createFlow
import com.example.flow.base.createFlowFromText
import com.example.flow.base.log
import com.example.flow.data.features.movie.SearchMovieRepository
import com.example.flow.data.features.movie.models.Movie
import com.example.flow.data.features.movie.models.MovieType
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber

class SearchMovieViewModel : ViewModel() {

    private val repository = SearchMovieRepository()
    private var currentJob: Job? = null
    private var dbMovieJob: Job? = null

    val movieListState = mutableStateListOf<Movie>()
    var progressState by mutableStateOf(false)
        private set
    var responseStatus by mutableStateOf(true)
        private set
    var networkAvailable by mutableStateOf(false)
        private set
    val dbMovieState = mutableStateListOf<Movie>()

    @ExperimentalCoroutinesApi
    fun createTextFlow(text: String): Flow<String> {
        return text.createFlowFromText()
    }

    @ExperimentalCoroutinesApi
    fun createTypeFlow(type: MovieType): Flow<MovieType> {
        return type.createFlow()
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun bind(textFlow: Flow<String>, typeFlow: Flow<MovieType>) {
        Timber.e("bind fun call")
        currentJob?.cancel()
        currentJob = combine(textFlow, typeFlow) { text, type -> text to type }
            .debounce(2000)
//            .onStart { emit(Pair("", MovieType.NOT_SELECTED)) }
            .log("Sample message")
            .distinctUntilChanged()
            .onEach {
                progressState = true
                responseStatus = true
            }
            .mapLatest { repository.searchMovie(it.first, it.second) }
            .catch { t -> Timber.e("Flow catch error $t") }
            .onEach { root ->
                if (root.response.toBoolean()) {
                    val movies = root.search.orEmpty()
                    responseStatus = true
                    movieListState.clear()
                    movieListState.addAll(movies)
                    repository.saveMovies(movies)
                } else {
                    delay(2000)
                    movieListState.clear()
                    responseStatus = false
                }
                progressState = false
                Timber.e("collect data is $root")
            }
            .launchIn(viewModelScope)
    }

    fun setNetworkConnectionAvailable(state: Boolean) {
        networkAvailable = state
    }

    fun getAllMovie() {
        dbMovieJob = repository.observeMovies()
            .onEach { Timber.e("Start db flow") }
            .onEach { movies ->
                dbMovieState.clear()
                dbMovieState.addAll(movies)
            }
            .launchIn(viewModelScope)
    }

    fun cancelJob() {
        currentJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
        dbMovieJob?.cancel()
    }
}