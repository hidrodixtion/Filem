package com.projectbox.filem.view

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.projectbox.filem.R
import com.projectbox.filem.alarm.ReminderReceiver
import com.projectbox.filem.util.TinyDB
import kotlinx.android.synthetic.main.activity_setting.*
import org.koin.android.ext.android.inject

class SettingActivity : AppCompatActivity() {

    companion object {
        const val K_DAILY_REMINDER = "dailyReminder"
        const val K_NEW_RELEASE = "newRelease"
    }

    private val pref: TinyDB by inject()
    private val reminderUtil: ReminderReceiver by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        initUi()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun initUi() {
        title = getString(R.string.settings)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        switch_daily.isChecked = pref.getBoolean(K_DAILY_REMINDER)
        switch_new_release.isChecked = pref.getBoolean(K_NEW_RELEASE)

        switch_daily.setOnCheckedChangeListener { _, isChecked ->
            activateReminder(isChecked, ReminderReceiver.ID_DAILY_REMINDER, 7)
            pref.putBoolean(K_DAILY_REMINDER, isChecked)
        }

        switch_new_release.setOnCheckedChangeListener { _, isChecked ->
            activateReminder(isChecked, ReminderReceiver.ID_NEW_RELEASE, 8)
            pref.putBoolean(K_NEW_RELEASE, isChecked)
        }

        btn_language.setOnClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }
    }

    private fun activateReminder(isActive: Boolean, id: Int = 0, hour: Int) {
        if (isActive)
            reminderUtil.createAlarm(id, hour)
        else
            reminderUtil.dismissAlarm(id)
    }
}
