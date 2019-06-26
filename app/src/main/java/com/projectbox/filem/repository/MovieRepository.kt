package com.projectbox.filem.repository

import com.projectbox.filem.model.Movie
import com.projectbox.filem.service.IService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/**
 * Created by adinugroho
 * Why Repository needs to be created?
 * Because ViewModel is an "Android" class so to test it purely using Unit Test / JUnit will require some configuration & hack
 * Also by using repository we can change the vm to use an API repository or DB repository
 */
class MovieRepository(private val service: IService) {
    suspend fun getMovieList(): List<Movie> = coroutineScope {
        val defer = async { service.getMovieList() }
        val response = defer.await()
        response.results
    }
}