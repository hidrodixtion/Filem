package com.projectbox.filem.db.dao

import androidx.room.*
import com.projectbox.filem.model.MovieTvShow

/**
 * Created by adinugroho
 */
@Dao
interface FavoriteDao {

    @Query("SELECT * FROM MovieTvShow WHERE releaseDate is not null")
    suspend fun getMovieFav(): List<MovieTvShow>

    @Query("SELECT * FROM MovieTvShow WHERE firstAirDate is not null")
    suspend fun getTvFav(): List<MovieTvShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFav(data: MovieTvShow)

    @Delete
    suspend fun removeFromFav(data: MovieTvShow)

    @Query("SELECT COUNT(1) FROM MovieTvShow WHERE id = :id")
    suspend fun isFavorite(id: String): Int
}