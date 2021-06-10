package id.herdroid.moviecatalog.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import id.herdroid.moviecatalog.api.source.DataRepository
import id.herdroid.moviecatalog.data.entity.MovieEntity
import id.herdroid.moviecatalog.data.entity.TvShowEntity
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

class DetailViewModelTest {
    private val dataMovie = DataDummy.dummyMovies()[0]
    private val movieId = dataMovie.movieId
    private val dataTvShow = DataDummy.dummyTvShows()[0]
    private val tvShowId = dataTvShow.tvShowId

    @Rule
    @JvmField
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: DetailViewModel
    private var dataRepository = mock(DataRepository::class.java)

    @Mock
    lateinit var movieObserver: Observer<MovieEntity>

    @Mock
    lateinit var tvShowObserver: Observer<TvShowEntity>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = DetailViewModel(dataRepository)
        viewModel.setSelectedData(movieId)
        viewModel.setSelectedData(tvShowId)
    }

    @Test
    fun getMovie() {
        val movie = MutableLiveData<MovieEntity>()
        movie.value = dataMovie

        `when`(dataRepository.getDetailMovie(movieId)).thenReturn(movie)
        val movieEntity = viewModel.getMovie().value as MovieEntity
        verify(dataRepository).getDetailMovie(movieId)
        assertNotNull(movieEntity)
        assertEquals(dataMovie.movieId, movieEntity.movieId)
        assertEquals(dataMovie.releaseDate, movieEntity.releaseDate)
        assertEquals(dataMovie.description, movieEntity.description)
        assertEquals(dataMovie.imagePath, movieEntity.imagePath)
        assertEquals(dataMovie.title, movieEntity.title)

        viewModel.getMovie().observeForever(movieObserver)
        verify(movieObserver).onChanged(dataMovie)
    }

    @Test
    fun getTvShow() {
        val tvShow = MutableLiveData<TvShowEntity>()
        tvShow.value = dataTvShow

        `when`(dataRepository.getDetailTvShow(tvShowId)).thenReturn(tvShow)
        val tvShowEntity = viewModel.getTvShow().value as TvShowEntity
        verify(dataRepository).getDetailTvShow(tvShowId)
        assertNotNull(tvShowEntity)
        assertEquals(dataTvShow.tvShowId, tvShowEntity.tvShowId)
        assertEquals(dataTvShow.releaseDate, tvShowEntity.releaseDate)
        assertEquals(dataTvShow.description, tvShowEntity.description)
        assertEquals(dataTvShow.imagePath, tvShowEntity.imagePath)
        assertEquals(dataTvShow.title, tvShowEntity.title)

        viewModel.getTvShow().observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dataTvShow)
    }


}