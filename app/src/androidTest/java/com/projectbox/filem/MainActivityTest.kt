package com.projectbox.filem

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.projectbox.filem.util.IdlingResourceUtil
import com.projectbox.filem.view.MainActivity
import com.projectbox.filem.view.MovieDetailActivity
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

//    @get:Rule
//    val detailActivityRule = ActivityTestRule(MovieDetailActivity::class.java)

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(IdlingResourceUtil.idleResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(IdlingResourceUtil.idleResource)
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().context
        assertEquals("com.projectbox.filem.test", appContext.packageName)
    }

    @Test
    fun testOpenDetail() {
        val recyclerView = onView(allOf(isDisplayed(), withId(R.id.recycler_view)))
        recyclerView.check(matches(isDisplayed()))

        recyclerView.perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withText(activityRule.activity.getString(R.string.overview))).check(matches(isDisplayed()))
    }

    @Test
    fun testAddToFavorite() {
        var recyclerView = onView(allOf(isDisplayed(), withId(R.id.recycler_view)))
        recyclerView.check(matches(isDisplayed()))

        recyclerView.perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withText(activityRule.activity.getString(R.string.overview))).check(matches(isDisplayed()))

        onView(withId(R.id.fab_favorite)).perform(click())

        val txtTitle = onView(withId(R.id.txt_title))
        val currentTitle = TestHelper.getText(txtTitle)!!

        Espresso.pressBack()

        onView(withId(R.id.menu_favorite)).perform(click())

        recyclerView = onView(allOf(isDisplayed(), withId(R.id.recycler_view)))
        recyclerView.perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withText(currentTitle)).check(matches(isDisplayed()))
    }
}
