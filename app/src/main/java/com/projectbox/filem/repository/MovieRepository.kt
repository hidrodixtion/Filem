package com.projectbox.filem.repository

import com.projectbox.filem.db.dao.FavoriteDao
import com.projectbox.filem.model.Cast
import com.projectbox.filem.model.MovieTvShow
import com.projectbox.filem.service.IService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by adinugroho
 * Why Repository needs to be created?
 * Because ViewModel is an "Android" class so to test it purely using Unit Test / JUnit will require some configuration & hack
 * Also by using repository we can change the vm to use an API repository or DB repository
 */
class MovieRepository(private val service: IService, private val dao: FavoriteDao) {
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

    suspend fun searchMovie(query: String): List<MovieTvShow> = coroutineScope {
        val defer = async { service.searchMovie(query) }
        val response = defer.await()
        response.results
    }

    suspend fun searchTvShow(query: String): List<MovieTvShow> = coroutineScope {
        val defer = async { service.searchTvShow(query) }
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

    suspend fun getFavoriteMovieList(): List<MovieTvShow> = coroutineScope {
        val defer = async { dao.getMovieFav() }
        defer.await()
    }

    suspend fun getFavoriteTvList(): List<MovieTvShow> = coroutineScope {
        val defer = async { dao.getTvFav() }
        defer.await()
    }

    suspend fun addToFavorite(item: MovieTvShow) = coroutineScope {
        val defer = async { dao.addToFav(item) }
        defer.await()
    }

    suspend fun removeFromFavorite(item: MovieTvShow) = coroutineScope {
        val defer = async { dao.removeFromFav(item) }
        defer.await()
    }

    suspend fun isFavorite(id: String): Boolean = coroutineScope {
        val defer = async { dao.isFavorite(id) }
        val result = defer.await()
        result == 1
    }

    suspend fun searchFavoriteMovie(query: String): List<MovieTvShow> = coroutineScope {
        val defer = async { dao.searchMovieFav(query) }
        defer.await()
    }

    suspend fun searchFavoriteTvShow(query: String): List<MovieTvShow> = coroutineScope {
        val defer = async { dao.searchTvShowFav("'%$query%'") }
        defer.await()
    }

    suspend fun getMovieDetail(id: String): MovieTvShow = coroutineScope {
        val defer = async { dao.getMovieDetail(id) }
        defer.await()
    }

    suspend fun getRecentRelease(): List<MovieTvShow> = coroutineScope {
        val now = Date()
        val strNow = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(now)
        val defer = async { service.getRecentRelease(strNow, strNow) }
        val response = defer.await()
        val list = response.results
        var maxSize = 5
        if (list.size < maxSize)
            maxSize = list.size
        list.subList(0, maxSize)
    }
}