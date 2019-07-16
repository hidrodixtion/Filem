package com.projectbox.filem.alarm

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.projectbox.filem.R
import com.projectbox.filem.model.MovieTvShow
import com.projectbox.filem.repository.MovieRepository
import com.projectbox.filem.view.MainActivity
import com.projectbox.filem.view.MovieDetailActivity
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext

/**
 * Created by adinugroho
 */
class ReminderReceiver: BroadcastReceiver(),
    CoroutineScope, KoinComponent {
    companion object {
        const val K_ALARM_ID = "alarmId"
        const val ID_DAILY_REMINDER = 1000
        const val ID_NEW_RELEASE = 2000
    }

    private val context: Context by inject()
    private val repository: MovieRepository by inject()

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onReceive(context: Context, anIntent: Intent?) {
        val intent = anIntent ?: return
        val id = intent.getIntExtra(K_ALARM_ID, ID_DAILY_REMINDER)

        when (id) {
            ID_DAILY_REMINDER -> {
                createNotification(
                    context,
                    context.getString(R.string.app_name), context.getString(R.string.content_daily_reminder), id
                )
            }
            ID_NEW_RELEASE -> {
                launch {
                    val movies = repository.getRecentRelease()
                    movies.forEachIndexed { position, movie ->
                        val content = context.getString(R.string.content_new_release, movie.movieTitle ?: "")
                        createNotification(
                            context,
                            context.getString(R.string.title_new_release), content, id + position + 1, movie
                        )
                    }
                    coroutineContext.cancelChildren()
                }
            }
        }
    }

    private fun createNotification(
        context: Context,
        title: String,
        content: String,
        notifId: Int,
        data: MovieTvShow? = null
    ) {
        val channelId = "channel_filem"
        val channelName = "filem reminder"

        val notifMan = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
        val notifBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_movie)
            .setContentTitle(title)
            .setContentText(content)
            .setAutoCancel(true)

        createPendingIntent(context, notifId, data)?.let {
            notifBuilder.setContentIntent(it)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notifBuilder.setChannelId(channelId)
            notifMan?.createNotificationChannel(channel)
        }

        val notification = notifBuilder.build()
        notifMan?.notify(notifId, notification)
    }

    private fun createPendingIntent(context: Context, notifId: Int, data: MovieTvShow?): PendingIntent? {
        val intent: Intent
        if (notifId == ID_DAILY_REMINDER) {
            intent = Intent(context.applicationContext, MainActivity::class.java)
        } else {
            intent = Intent(context.applicationContext, MovieDetailActivity::class.java)
            data?.let {
                intent.putExtra("data", data)
            }
        }

        return PendingIntent.getActivity(context.applicationContext, notifId, intent, 0)
    }

    fun createAlarm(id: Int, hour: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        intent.putExtra(K_ALARM_ID, id)

        val now = Calendar.getInstance()
        now.set(Calendar.HOUR, hour)
        now.set(Calendar.MINUTE, 0)
        now.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager?.setRepeating(AlarmManager.RTC_WAKEUP, now.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }

    fun dismissAlarm(id: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0)
        pendingIntent.cancel()
        alarmManager?.cancel(pendingIntent)
    }
}