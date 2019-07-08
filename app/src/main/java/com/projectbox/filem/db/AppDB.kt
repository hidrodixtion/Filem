package com.projectbox.filem.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.projectbox.filem.db.dao.FavoriteDao
import com.projectbox.filem.model.MovieTvShow

/**
 * Created by adinugroho
 */
@Database(entities = arrayOf(MovieTvShow::class), version = 1)
abstract class AppDB: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        fun getDatabase(context: Context): AppDB = Room.databaseBuilder(context, AppDB::class.java, "app.db").build()

        fun getTestDatabase(context: Context): AppDB = Room.inMemoryDatabaseBuilder(context, AppDB::class.java).build()
    }
}