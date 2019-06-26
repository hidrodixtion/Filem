package com.projectbox.filem

import com.projectbox.filem.model.Movie
import com.projectbox.filem.repository.MovieRepository
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito.given

/**
 * Created by adinugroho
 */
class MovieRepoTest : KoinTest {

    private val movieRepo: MovieRepository by inject()

    @Before
    fun before() {
        startKoin {
            modules(KoinModules().appModules)
        }

        declareMock<MovieRepository> {
            given(
                runBlocking {
                    getMovieList()
                }
            ).will { emptyList<Movie>() }
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `test movie repo is not null`() {
        assertNotNull(movieRepo)
    }

    @Test
    fun `test get movie is not null`() {
        runBlocking(Unconfined) {
            val list = movieRepo.getMovieList()

            assertNotNull(list)
        }
    }
}