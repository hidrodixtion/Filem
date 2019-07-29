package com.projectbox.filem

<<<<<<< HEAD
=======
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.projectbox.filem.model.AppResult
>>>>>>> jetpack_submission_1
import com.projectbox.filem.model.MovieTvShow
import com.projectbox.filem.repository.MovieRepository
import com.projectbox.filem.util.observeOnce
import com.projectbox.filem.viewmodel.MovieListVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito.given
import org.mockito.Mockito

/**
 * Created by adinugroho
 */
class MovieRepoTest : AutoCloseKoinTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val movieRepo: MovieRepository by inject()

    private val movieListVM: MovieListVM by inject()

    private val aMovieMock = MovieTvShow(
        id = "123",
        poster = "",
        movieTitle = "Movie 123",
        overview = "",
        firstAirDate = "2019-07-24",
        releaseDate = "2019-07-24",
        showTitle = "Show 123",
        vote = 9.0,
        voteCount = 1000.0
    )

    @Before
    fun before() {
        Dispatchers.setMain(mainThreadSurrogate)

        startKoin {
            modules(KoinModules().appModules)
        }

        declareMock<MovieRepository> {
            given(
                runBlocking { getMovieList() }
<<<<<<< HEAD
            ).will { emptyList<MovieTvShow>() }
=======
            ).will { listOf(aMovieMock) }
>>>>>>> jetpack_submission_1

            given(
                runBlocking { getTvShowList() }
            ).will { listOf(aMovieMock) }
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test movie repo is not null`() {
        assertNull(movieRepo)
    }

    @Test
    fun `test get movie is not null`() {
        runBlocking(Unconfined) {
            val list = movieRepo.getMovieList()

            Mockito.verify(movieRepo).getMovieList()
            assertNotNull(list)
            assertEquals(1, list.size)
        }
    }

    @Test
    fun `test get tvshow is not null`() {
        runBlocking(Unconfined) {
            val list = movieRepo.getTvShowList()

            Mockito.verify(movieRepo).getTvShowList()
            assertNotNull(list)
            assertEquals(1, list.size)
        }
    }

    @Test
    fun `test get movie list from VM has 1 item`() {
        movieListVM.getMovies()

        movieListVM.itemList.observeOnce {
            assertThat(it is AppResult.Success).isTrue()

            when(it) {
                is AppResult.Success -> assertEquals(1, it.data.size)
            }
        }
    }

    @Test
    fun `test get tvshow list from VM has 1 item`() {
        movieListVM.getTvShow()
        movieListVM.itemList.observeOnce {
            assertThat(it is AppResult.Success).isTrue()

            when(it) {
                is AppResult.Success -> assertEquals(1, it.data.size)
            }
        }
    }
}