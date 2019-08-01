package com.projectbox.filem.db.dao

import androidx.paging.DataSource
import androidx.room.*
import com.projectbox.filem.model.MovieTvShow

/**
 * Created by adinugroho
 */
@Dao
interface FavoriteDao {

    @Query("SELECT * FROM MovieTvShow WHERE releaseDate is not null")
    fun getMovieFav(): DataSource.Factory<Int, MovieTvShow>

    @Query("SELECT * FROM MovieTvShow WHERE firstAirDate is not null")
    fun getTvFav(): DataSource.Factory<Int, MovieTvShow>

    @Query("SELECT * FROM MovieTvShow WHERE movieTitle IS NOT NULL AND movieTitle LIKE '%' || :query || '%'")
    fun searchMovieFav(query: String): List<MovieTvShow>

    @Query("SELECT * FROM MovieTvShow WHERE showTitle IS NOT NULL AND showTitle LIKE :query")
    suspend fun searchTvShowFav(query: String): List<MovieTvShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFav(data: MovieTvShow)

    @Delete
    suspend fun removeFromFav(data: MovieTvShow)

    @Query("SELECT COUNT(1) FROM MovieTvShow WHERE id = :id")
    suspend fun isFavorite(id: String): Int

    @Query("SELECT * FROM MovieTvShow WHERE id = :id LIMIT 1")
    suspend fun getMovieDetail(id: String): MovieTvShow
}