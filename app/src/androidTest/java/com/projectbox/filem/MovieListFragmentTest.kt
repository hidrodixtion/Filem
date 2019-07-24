package com.projectbox.filem

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.projectbox.filem.model.ListType
import com.projectbox.filem.view.MovieTvListFragment
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by adinugroho
 */
@RunWith(AndroidJUnit4::class)
class MovieListFragmentTest {
    @Test
    fun testRecyclerView() {
        val fragmentArgs = Bundle().apply {
            putString(MovieTvListFragment.LIST_TYPE, ListType.MOVIE.name)
        }
        val scenario = launchFragmentInContainer<MovieTvListFragment>(fragmentArgs)
        val recyclerView = onView(allOf(isDisplayed(), withId(R.id.recycler_view)))
        recyclerView.check(matches(isDisplayed()))
    }
}