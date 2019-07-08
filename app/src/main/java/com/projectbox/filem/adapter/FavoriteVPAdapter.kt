package com.projectbox.filem.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.projectbox.filem.R
import com.projectbox.filem.model.ListType
import com.projectbox.filem.view.MovieTvListFragment

/**
 * Created by adinugroho
 */
class FavoriteVPAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        val listType = ListType.values()[position]
        return MovieTvListFragment.getInstance(listType, true)
    }

    override fun getCount(): Int = ListType.values().size

    override fun getPageTitle(position: Int): CharSequence? {
        val listType = ListType.values()[position]
        return when (listType) {
            ListType.MOVIE -> context.resources.getString(R.string.menu_movie)
            ListType.TVSHOW -> context.resources.getString(R.string.menu_tv)
        }
    }
}