package com.projectbox.filem

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.projectbox.filem.model.MovieTvShow
import com.projectbox.filem.repository.MovieRepository
import com.projectbox.filem.view.MainActivity
import com.projectbox.filem.viewmodel.MovieListVM
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest: AutoCloseKoinTest() {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    private val aMovieMock = MovieTvShow(
        id = "420818",
        poster = "/dzBtMocZuJbjLOXvrl4zGYigDzh.jpg",
        movieTitle = "Lion King",
        overview = "Simba idolises his father, King Mufasa, and takes to heart his own royal destiny. But not everyone in the kingdom celebrates the new cub's arrival. Scar, Mufasa's brother—and former heir to the throne—has plans of his own. The battle for Pride Rock is ravaged with betrayal, tragedy and drama, ultimately resulting in Simba's exile. With help from a curious pair of newfound friends, Simba will have to figure out how to grow up and take back what is rightfully his.",
        firstAirDate = null,
        releaseDate = "2019-07-12",
        showTitle = null,
        vote = 7.2,
        voteCount = 697.0
    )

    private val aTVShowMock = MovieTvShow(
        id = "60735",
        poster = "/fki3kBlwJzFp8QohL43g9ReV455.jpg",
        movieTitle = null,
        overview = "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \"meta-human\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
        firstAirDate = "2014-10-07",
        releaseDate = null,
        showTitle = "The Flash",
        vote = 6.7,
        voteCount = 6274797.0
    )

    private val movieRepo: MovieRepository by inject()

    @Before
    fun setup() {
        val overriddenVM = module(override = true) {
            viewModel { MovieListVM(get()) }
        }

        stopKoin()
        startKoin {
            modules(listOf(KoinModules().appModules, overriddenVM))
        }

        declareMock<MovieRepository> {
            BDDMockito.given(
                runBlocking { getMovieList() }
            ).will { listOf(aMovieMock) }

            BDDMockito.given(
                runBlocking { getTvShowList() }
            ).will { listOf(aTVShowMock) }
        }
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().context
        assertEquals("com.projectbox.filem.test", appContext.packageName)
    }

    @Test
    fun testClickItemInList() {
        val listview = onView(allOf(isDisplayed(), withId(R.id.recycler_view)))
        listview.check(matches(isDisplayed()))
    }
}
