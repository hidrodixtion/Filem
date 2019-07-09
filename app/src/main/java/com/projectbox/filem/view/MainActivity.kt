package com.projectbox.filem.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.projectbox.filem.R
import com.projectbox.filem.model.ListType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val SELECTED_MENU = "selectedMenu"
        private const val SELECTED_TAB = "selectedTab"
    }

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_MENU, bottom_navigation.selectedItemId)
        outState.putInt(SELECTED_TAB, tab.selectedTabPosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.let {
            if (it.getInt(SELECTED_MENU) == R.id.menu_favorite) {
                val item = tab.getTabAt(it.getInt(SELECTED_TAB))
                bottom_navigation.selectedItemId = R.id.menu_favorite
                item?.select()
            }
        }
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
            R.id.menu_movie -> refreshViewPagerWithAdapter(ListType.MOVIE)
            R.id.menu_tv -> refreshViewPagerWithAdapter(ListType.TVSHOW)
            R.id.menu_favorite -> refreshViewPagerWithAdapter(ListType.MOVIE, true)
        }
        return@OnNavigationItemSelectedListener true
    }

    private val onTabSelected = object: TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tabItem: TabLayout.Tab?) {}

        override fun onTabUnselected(tabItem: TabLayout.Tab?) {}

        override fun onTabSelected(tabItem: TabLayout.Tab?) {
            when (tab.selectedTabPosition) {
                0 -> refreshViewPagerWithAdapter(ListType.MOVIE, true)
                1 -> refreshViewPagerWithAdapter(ListType.TVSHOW, true)
            }
        }
    }

    private fun initUi() {
        bottom_navigation.setOnNavigationItemSelectedListener(onBottomNavSelected)

        supportActionBar?.title = resources.getString(R.string.app_name)

        // Not using a viewpager here, because it doesn't go well with rotation change
        tab.addTab(tab.newTab().setText(resources.getString(R.string.menu_movie)))
        tab.addTab(tab.newTab().setText(resources.getString(R.string.menu_tv)))
        tab.addOnTabSelectedListener(onTabSelected)
    }

    private fun refreshViewPagerWithAdapter(listType: ListType, isShowingFavorite: Boolean = false) {
        if (isShowingFavorite)
            tab.visibility = View.VISIBLE
        else
            tab.visibility = View.GONE

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MovieTvListFragment.getInstance(listType, isShowingFavorite))
            .commit()
    }
}
