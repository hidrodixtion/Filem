package com.projectbox.filem.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.projectbox.filem.R
import com.projectbox.filem.model.ListType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUi()

        if (savedInstanceState == null)
            bottom_navigation.selectedItemId = R.id.menu_movie
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.setting, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val selectedItem = item ?: return false

        when(selectedItem.itemId) {
            R.id.menu_change_language -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private val onBottomNavSelected = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.menu_movie -> supportFragmentManager.beginTransaction().replace(R.id.container, MovieTvListFragment.getInstance(ListType.MOVIE)).commit()
            R.id.menu_tv -> supportFragmentManager.beginTransaction().replace(R.id.container, MovieTvListFragment.getInstance(ListType.TVSHOW)).commit()
        }
        return@OnNavigationItemSelectedListener true
    }

    private fun initUi() {
        bottom_navigation.setOnNavigationItemSelectedListener(onBottomNavSelected)
    }
}
