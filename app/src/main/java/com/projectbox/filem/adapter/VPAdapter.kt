package com.projectbox.filem.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.projectbox.filem.model.ListType
import com.projectbox.filem.view.MovieTvListFragment

/**
 * Created by adinugroho
 */
class VPAdapter(fm: FragmentManager, private val type: ListType) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment = MovieTvListFragment.getInstance(type)

    override fun getCount(): Int = 1
}