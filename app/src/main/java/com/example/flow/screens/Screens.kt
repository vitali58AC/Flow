package com.example.flow.screens

/*enum class Screens {
    Home,
    SearchMovie,
    DatabaseMovie
}*/

sealed class Screens(val route: String) {
    object Home : Screens("Home")
    enum class DatabaseMovie {
        DatabaseRoot,
        DatabaseMovieList
    }

    enum class SearchMovie {
        SearchMovieRoot,
        SearchMovieList
    }
}
