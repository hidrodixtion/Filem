package com.projectbox.filem.service

import com.projectbox.filem.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by adinugroho
 */
interface IService {
    @GET("discover/movie")
    suspend fun getMovieList(@Query("with_genres") genreId: Int = 28): MovieResponse//Observable<ScheduleResponse>
}