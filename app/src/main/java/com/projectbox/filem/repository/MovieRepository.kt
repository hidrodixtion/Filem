package com.projectbox.filem.repository

import com.projectbox.filem.model.Cast
import com.projectbox.filem.model.MovieTvShow
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
    suspend fun getMovieList(): List<MovieTvShow> = coroutineScope {
        val defer = async { service.getMovieList() }
        val response = defer.await()
        response.results
    }

    suspend fun getTvShowList(): List<MovieTvShow> = coroutineScope {
        val defer = async { service.getTvShowList() }
        val response = defer.await()
        response.results
    }

    suspend fun getMovieCast(movieId: String): List<Cast> = coroutineScope {
        val defer = async { service.getMovieCredit(movieId)}
        val response = defer.await()
        response.cast
    }

    suspend fun getTvCast(tvId: String): List<Cast> = coroutineScope {
        val defer = async { service.getTvCredit(tvId)}
        val response = defer.await()
        response.cast
    }
}