package com.projectbox.filem

import com.projectbox.filem.model.MovieTvShow
import com.projectbox.filem.repository.MovieRepository
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
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

    private val movieRepo: MovieRepository by inject()

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
        startKoin {
            modules(KoinModules().appModules)
        }

        declareMock<MovieRepository> {
            given(
                runBlocking { getMovieList() }
            ).will { listOf(aMovieMock) }

            given(
                runBlocking { getTvShowList() }
            ).will { listOf(aMovieMock) }
        }
    }

    @Test
    fun `test movie repo is not null`() {
        assertNotNull(movieRepo)
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
}