package com.projectbox.filem.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.projectbox.filem.R
import com.projectbox.filem.event.SearchEvent
import com.projectbox.filem.model.ListType
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    companion object {
        private const val SELECTED_MENU = "selectedMenu"
        private const val SELECTED_TAB = "selectedTab"
        private const val SEARCH_QUERY = "searchQuery"
    }

    lateinit var searchView: SearchView
    lateinit var searchMenuItem: MenuItem
    var searchQuery = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUi()

        if (savedInstanceState == null) {
            bottom_navigation.selectedItemId = R.id.menu_movie
            handleIntent(intent)
        } else {
            searchQuery = savedInstanceState.getString(SEARCH_QUERY, "")
        }
    }

    override fun onNewIntent(intent: Intent?) {
        Timber.v("NEW INTENT")
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(anIntent: Intent?) {
        val intent = anIntent ?: return

        // Handle when search occured, only propagate search event when the last search query is not the same as current query
        // Doing it this way also prevent the app to re-fetch the search list.
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            if (query != searchQuery)
                EventBus.getDefault().post(SearchEvent.Query(query))

            searchQuery = query
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.setting, menu)

        searchMenuItem = menu.findItem(R.id.search)
        searchView = menu.findItem(R.id.search).actionView as SearchView

        initSearchView()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putInt(SELECTED_MENU, bottom_navigation.selectedItemId)
            putInt(SELECTED_TAB, tab.selectedTabPosition)
            putString(SEARCH_QUERY, searchView.query.toString())
        }
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

        when (selectedItem.itemId) {
            R.id.menu_change_language -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private val onBottomNavSelected = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.menu_movie -> refreshFragmentContainer(ListType.MOVIE)
            R.id.menu_tv -> refreshFragmentContainer(ListType.TVSHOW)
            R.id.menu_favorite -> refreshFragmentContainer(ListType.MOVIE, true)
        }
        return@OnNavigationItemSelectedListener true
    }

    private val onTabSelected = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tabItem: TabLayout.Tab?) {}

        override fun onTabUnselected(tabItem: TabLayout.Tab?) {}

        override fun onTabSelected(tabItem: TabLayout.Tab?) {
            when (tab.selectedTabPosition) {
                0 -> refreshFragmentContainer(ListType.MOVIE, true)
                1 -> refreshFragmentContainer(ListType.TVSHOW, true)
            }
        }
    }

    private fun initUi() {
        bottom_navigation.setOnNavigationItemSelectedListener(onBottomNavSelected)

        supportActionBar?.title = resources.getString(R.string.app_name)

        // Not using a viewpager here, because it doesn't go well with rotation change
        tab.apply {
            addTab(tab.newTab().setText(resources.getString(R.string.menu_movie)))
            addTab(tab.newTab().setText(resources.getString(R.string.menu_tv)))
            addOnTabSelectedListener(onTabSelected)
        }
    }

    private fun initSearchView() {
        val searchMan = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.apply {
            setSearchableInfo(searchMan.getSearchableInfo(componentName))
            setIconifiedByDefault(false)
            isSubmitButtonEnabled = true

            if (searchQuery.isNotEmpty()) {
                searchMenuItem.expandActionView()
                setQuery(searchQuery, true)
                clearFocus()
            }
        }

        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                EventBus.getDefault().post(SearchEvent.Started())
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                EventBus.getDefault().post(SearchEvent.Closed())
                return true
            }
        })
    }

    private fun refreshFragmentContainer(listType: ListType, isShowingFavorite: Boolean = false) {
        if (isShowingFavorite)
            tab.visibility = View.VISIBLE
        else
            tab.visibility = View.GONE

        restoreSearchView()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MovieTvListFragment.getInstance(listType, isShowingFavorite))
            .commit()
    }

    private fun restoreSearchView() {
        if (::searchMenuItem.isInitialized) {
            searchMenuItem.collapseActionView()
            searchView.setQuery("", false)
            searchView.clearFocus()
        }
    }
}
