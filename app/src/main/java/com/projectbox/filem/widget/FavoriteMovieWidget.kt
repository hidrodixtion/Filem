package com.projectbox.filem.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import android.widget.Toast
import com.projectbox.filem.R
import com.projectbox.filem.model.MovieTvShow
import com.projectbox.filem.view.MovieDetailActivity

/**
 * Implementation of App Widget functionality.
 */
class FavoriteMovieWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        if (intent != null && intent.action != null) {
            if (intent.action == DETAIL_ACTION) {
                val detailIntent = Intent(context, MovieDetailActivity::class.java)
                val id = intent.getStringExtra("id")
                detailIntent.putExtra("id", id)
                detailIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                Toast.makeText(context, data, Toast.LENGTH_SHORT).show()
                context?.startActivity(detailIntent)
            }
        }
    }

    companion object {

        const val DETAIL_ACTION = "com.projectbox.filem.DETAIL_ACTION"

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val intent = Intent(context, StackViewService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

            val views = RemoteViews(context.packageName, R.layout.favorite_movie_widget)
            views.setRemoteAdapter(R.id.stack_view, intent)

            val openDetailIntent = Intent(context, FavoriteMovieWidget::class.java)
            openDetailIntent.action = DETAIL_ACTION
            openDetailIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            val pendingDetailIntent = PendingIntent.getBroadcast(context, 100, openDetailIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setPendingIntentTemplate(R.id.stack_view, pendingDetailIntent)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

