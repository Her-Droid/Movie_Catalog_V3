package id.herdroid.moviecatalog.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import id.herdroid.moviecatalog.api.source.DataRepository
import id.herdroid.moviecatalog.data.entity.MovieEntity
import id.herdroid.moviecatalog.utils.DataDummy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import kotlin.jvm.Throws

class MovieViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: MovieViewModel
    private var dataRepository = mock(DataRepository::class.java)

    @Mock
    lateinit var observer: Observer<List<MovieEntity>>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MovieViewModel(dataRepository)

    }


    @Test
    fun getMovies(){
        val dummyMovie = DataDummy.dummyMovies()
        val dataMovies = MutableLiveData<List<MovieEntity>>()
        dataMovies.value = dummyMovie

        `when`(dataRepository.getListMovie()).thenReturn(dataMovies)
        val movie = viewModel.loadMovies(page).value
        verify(dataRepository).getListMovie()
        assertNotNull(movie)
        assertEquals(10, movie?.size)

        viewModel.loadMovies(page).observeForever(observer)
        verify(observer).onChanged(dummyMovie)
    }
}