package com.example.flow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.flow.data.network.ConnectionLiveData
import com.example.flow.screens.Navigation
import com.example.flow.screens.search_movie.SearchMovieViewModel
import com.example.flow.ui.theme.FlowTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber
import kotlin.random.Random

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : ComponentActivity() {

    private val searchMovieViewModel: SearchMovieViewModel by viewModels()
    private lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionLiveData = ConnectionLiveData(this)
        setContent {
            val navController = rememberNavController()
            val scope = rememberCoroutineScope()
            //Flow example
            val generator = createFlowGenerator()
            var currentJob: Job? = null
            var generatorExample by remember { mutableStateOf(0) }
/*            LaunchedEffect(key1 = true) {
                Timber.e("Launched effect relaunch")
                currentJob?.cancel()
                //Подход, что бы новое задание отменяло предыдущее
                currentJob = scope.launch {
                    generator
                        .map { it.toDouble() / 100000.0 }
                        //Терминальная функция
                        .collect {
                            //consumer
                            Timber.e("consume value = $it")
                            generatorExample = it.toInt()
                        }
                }
            }*/
            FlowTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val isNetworkAvailable = connectionLiveData.observeAsState(false).value
                    searchMovieViewModel.setNetworkConnectionAvailable(isNetworkAvailable)
                    Navigation(
                        searchMovieViewModel = searchMovieViewModel,
                        navController = navController,
                        generatorValue = generatorExample,
                    )
                }
            }
        }
    }
}

//Flow example
//producer
private fun createFlowGenerator(): Flow<Int> {
    return flow {
        Timber.e("Flow start")
        while (true) {
            val value = Random.nextInt()
            Timber.e("emit")
            emit(value = value)
            delay(1000)
        }
    }
}

//Примеры разных билдеров flow
@FlowPreview
private fun flowBuilders() {
    //Под капотом вызывает emit в цикде для каждого элемента.
    flowOf(1, 2, 3)
    val flowFromSuspendLambda = suspend {
        delay(1000)
        10
    }.asFlow()
    (0..100).asFlow()
    arrayOf(1, 2, 3, 4).asFlow()
    listOf(1, 2, 3, 4).asFlow()
}