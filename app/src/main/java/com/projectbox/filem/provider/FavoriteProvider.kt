package com.projectbox.filem.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.projectbox.filem.model.MovieTvShow
import com.projectbox.filem.repository.MovieRepository
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class FavoriteProvider : ContentProvider(), CoroutineScope {

    private val movieRepo: MovieRepository by inject()
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("com.projectbox.filem", "favorite", 1)
    }

    val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun shutdown() {
        job.cancelChildren()
        super.shutdown()
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to delete one or more rows")
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Implement this to handle requests to insert a new row.")
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        var movies = emptyList<MovieTvShow>()

        if (uriMatcher.match(uri) == 1) {
//            movies = runBlocking {
//                movieRepo.getFavoriteMovieList()
//            }
        }

        Timber.v(movies.size.toString())

        if (movies.isEmpty()) return null

        val tableTitle = listOf("id", "title", "overview", "vote_average", "poster", "releaseDate")
        val cursor = MatrixCursor(tableTitle.toTypedArray())
        movies.forEach {
            cursor.newRow()
                .add("id", it.id)
                .add("title", it.movieTitle)
                .add("overview", it.overview)
                .add("vote_average", it.vote)
                .add("poster", it.poster)
                .add("releaseDate", it.releaseDate)
        }

        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }
}
