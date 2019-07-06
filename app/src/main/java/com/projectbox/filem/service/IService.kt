package com.projectbox.filem.service

import com.projectbox.filem.model.CastResponse
import com.projectbox.filem.model.MovieTvShowResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by adinugroho
 */
interface IService {
    @GET("discover/movie")
    suspend fun getMovieList(): MovieTvShowResponse

    @GET("discover/tv")
    suspend fun getTvShowList(): MovieTvShowResponse

    @GET("movie/{movieId}/credits")
    suspend fun getMovieCredit(@Path("movieId") movieId: String): CastResponse
}