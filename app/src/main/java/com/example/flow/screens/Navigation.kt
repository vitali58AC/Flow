package com.example.flow.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.flow.screens.bd_movie.DatabaseMovieScreen
import com.example.flow.screens.home.HomeScreen
import com.example.flow.screens.search_movie.SearchMovieScreen
import com.example.flow.screens.search_movie.SearchMovieViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import timber.log.Timber

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun Navigation(
    searchMovieViewModel: SearchMovieViewModel,
    navController: NavHostController,
    generatorValue: Int,
) {
    NavHost(navController = navController, startDestination = Screens.Home.route) {
        composable(Screens.Home.route) {
            HomeScreen(
                onSearchClick = { navController.navigate(Screens.SearchMovie.SearchMovieRoot.name) },
                onMovieDatabaseClick = { navController.navigate(Screens.DatabaseMovie.DatabaseRoot.name) },
                generatorValue = generatorValue
            )
        }
        navDatabaseMovie(searchMovieViewModel = searchMovieViewModel)
        navSearchMovie(
            navController = navController,
            searchMovieViewModel = searchMovieViewModel,
        )
    }
}

fun NavGraphBuilder.navDatabaseMovie(searchMovieViewModel: SearchMovieViewModel) {
    navigation(
        route = Screens.DatabaseMovie.DatabaseRoot.name,
        startDestination = Screens.DatabaseMovie.DatabaseMovieList.name
    ) {
        composable(Screens.DatabaseMovie.DatabaseMovieList.name) {
            NoSideEffect(searchMovieViewModel::getAllMovie)
            DatabaseMovieScreen(
                movieList = searchMovieViewModel.dbMovieState
            )
        }
    }
}

@FlowPreview
@ExperimentalCoroutinesApi
fun NavGraphBuilder.navSearchMovie(
    navController: NavHostController,
    searchMovieViewModel: SearchMovieViewModel,
) {
    navigation(
        route = Screens.SearchMovie.SearchMovieRoot.name,
        startDestination = Screens.SearchMovie.SearchMovieList.name
    ) {
        composable(Screens.SearchMovie.SearchMovieList.name) {
            SearchMovieScreen(
                movieList = searchMovieViewModel.movieListState,
                progressIndicator = searchMovieViewModel.progressState,
                responseStatus = searchMovieViewModel.responseStatus,
                isNetworkAvailable = searchMovieViewModel.networkAvailable,
                onChangeData = { name, type ->
                    val nameFlow = searchMovieViewModel.createTextFlow(name)
                    val typeFlow = searchMovieViewModel.createTypeFlow(type)
                    searchMovieViewModel.bind(nameFlow, typeFlow)
                },
/*                onChangeDataFlow = { textFlow, typeFlow ->
                    searchMovieViewModel.bind(textFlow, typeFlow)
                },*/
                onBack = {
                    Timber.e("on back pressed")
                    navController.navigateUp()
                    searchMovieViewModel.cancelJob()
                }
            )
        }
    }
}
