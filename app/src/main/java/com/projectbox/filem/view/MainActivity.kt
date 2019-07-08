package com.projectbox.filem.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.projectbox.filem.R
import com.projectbox.filem.adapter.FavoriteVPAdapter
import com.projectbox.filem.adapter.VPAdapter
import com.projectbox.filem.model.ListType
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar.*

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
            R.id.menu_movie -> refreshViewPagerWithAdapter(VPAdapter(supportFragmentManager, ListType.MOVIE))
            R.id.menu_tv -> refreshViewPagerWithAdapter(VPAdapter(supportFragmentManager, ListType.TVSHOW))
            R.id.menu_favorite -> refreshViewPagerWithAdapter(FavoriteVPAdapter(supportFragmentManager, this))
        }
        return@OnNavigationItemSelectedListener true
    }

    private fun initUi() {
        bottom_navigation.setOnNavigationItemSelectedListener(onBottomNavSelected)

        tab.setupWithViewPager(view_pager)
        setSupportActionBar(toolbar)
        supportActionBar?.title = resources.getString(R.string.app_name)
    }

    private fun refreshViewPagerWithAdapter(adapter: FragmentPagerAdapter) {
        if (adapter.count == 1) {
            tab.visibility = View.GONE
        } else {
            tab.visibility = View.VISIBLE
        }

        val transaction = supportFragmentManager.beginTransaction()
        supportFragmentManager.fragments.forEach {
            transaction.remove(it)
        }
        transaction.commitNow()

        view_pager.removeAllViews()
        view_pager.adapter = null
        view_pager.adapter = adapter
    }
}
