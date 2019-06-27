package com.projectbox.filem.service

import com.projectbox.filem.model.MovieTvShowResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by adinugroho
 */
interface IService {
    @GET("discover/movie")
    suspend fun getMovieList(@Query("with_genres") genreId: Int = 28): MovieTvShowResponse

    @GET("discover/tv")
    suspend fun getTvShowList(@Query("with_genres") genreId: Int = 28): MovieTvShowResponse
}