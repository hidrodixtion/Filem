package com.projectbox.filem

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.google.common.truth.Truth
import com.projectbox.filem.model.AppResult
import com.projectbox.filem.model.MovieTvShow
import com.projectbox.filem.repository.MovieRepository
import com.projectbox.filem.util.observeOnce
import com.projectbox.filem.viewmodel.MovieListVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.mockito.Mockito.mock

/**
 * Created by adinugroho
 */
class FavoriteTest : AutoCloseKoinTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val movieListVM: MovieListVM by inject()

    @Before
    fun before() {
        Dispatchers.setMain(mainThreadSurrogate)

        startKoin {
            modules(KoinModules().appModules)
        }

        val dummyLiveData = MutableLiveData<PagedList<MovieTvShow>>()
        val dummyPagedList: PagedList<MovieTvShow> = mock(PagedList::class.java) as PagedList<MovieTvShow>
        dummyLiveData.value = dummyPagedList

        declareMock<MovieRepository> {
            BDDMockito.given(
                runBlocking { getFavoriteMovieList() }
            ).will { dummyLiveData }

            BDDMockito.given(
                runBlocking { getFavoriteTvList() }
            ).will { dummyLiveData }
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test get favorite movie`() {
        movieListVM.getFavMovies().observeOnce {
            Assert.assertNotNull(it)
        }
    }

    @Test
    fun `test get favorite tvshows`() {
        movieListVM.getFavTvShows().observeOnce {
            Assert.assertNotNull(it)
        }
    }
}