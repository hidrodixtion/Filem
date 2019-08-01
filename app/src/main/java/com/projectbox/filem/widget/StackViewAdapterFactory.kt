package com.projectbox.filem.widget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.projectbox.filem.BuildConfig
import com.projectbox.filem.R
import com.projectbox.filem.model.MovieTvShow
import com.projectbox.filem.repository.MovieRepository
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

/**
 * Created by adinugroho
 */

class StackViewService: RemoteViewsService() {
    private val adapterFactory by inject<StackViewAdapterFactory>()

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory = adapterFactory
}

class StackViewAdapterFactory(private val context: Context, private val repo: MovieRepository) : RemoteViewsService.RemoteViewsFactory, CoroutineScope {

    private val job = SupervisorJob()
    private var favoriteMovies = emptyList<MovieTvShow>()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate() {}

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    override fun onDataSetChanged() {
//        runBlocking {
//            favoriteMovies = repo.getFavoriteMovieList()
//        }
    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.item_movie_widget)

        val data = favoriteMovies[position]
        val url = "${BuildConfig.BIG_IMAGE_URL}${data.poster}"

        Timber.v(url)

        val bitmap = Glide.with(context).asBitmap().load(url).submit().get()
        rv.setImageViewBitmap(R.id.img_poster, bitmap)
        rv.setTextViewText(R.id.txt_title, data.movieTitle ?: "")

        val bundle = Bundle()
        bundle.putString("id", data.id)
        val intent = Intent()
        intent.putExtras(bundle)

        rv.setOnClickFillInIntent(R.id.img_poster, intent)

        return rv
    }

    override fun getCount(): Int = favoriteMovies.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
        coroutineContext.cancelChildren()
    }
}