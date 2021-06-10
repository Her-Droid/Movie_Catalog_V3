package id.herdroid.moviecatalog.api.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import org.mockito.Mockito.mock
import id.herdroid.moviecatalog.api.remote.RemoteDataRepository
import id.herdroid.moviecatalog.data.entity.MovieEntity
import id.herdroid.moviecatalog.data.entity.TvShowEntity
import id.herdroid.moviecatalog.database.LocalDataSource
import id.herdroid.moviecatalog.utils.AppExecutors
import id.herdroid.moviecatalog.utils.DataDummy
import id.herdroid.moviecatalog.utils.LiveDataTestUtil
import id.herdroid.moviecatalog.utils.PagedListUtil
import id.herdroid.moviecatalog.vo.Resource
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class DataRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataRepository::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val dataRepository = FakeDataRepository(remote, local, appExecutors)

    private val movieResponse =  DataDummy.dummyMovies()

    private val tvShowResponse =  DataDummy.dummyTvShows()

    private val dummyMovies = DataDummy.dummyMovies()[0]
    private val dummyTvShow = DataDummy.dummyTvShows()[0]

    @Test
    fun getListMovie() {
        val dataSourceMovie = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getMovies()).thenReturn(dataSourceMovie)
        dataRepository.getAllMovies()

        val movie = Resource.success(PagedListUtil.mockPagedList(DataDummy.dummyMovies()))
        Mockito.verify(local).getMovies()
        assertNotNull(movie.data)
        assertEquals(movieResponse.size.toLong(), movie.data?.size?.toLong())
    }

    @Test
    fun getMovieById(){
        val detailMovie = MutableLiveData<MovieEntity>()
        detailMovie.value = dummyMovies
        `when` (local.getDetailMovie(dummyMovies.movieId)).thenReturn(detailMovie)

        val data = LiveDataTestUtil.getValue(dataRepository.getDetailMovie(dummyMovies.movieId))
        verify(local).getDetailMovie(dummyMovies.movieId)
        assertNotNull(data.title)
        assertEquals(dummyMovies.title, data.title)
        assertNotNull(data.description)
        assertEquals(dummyMovies.description, data.description)
        assertNotNull(data.releaseDate)
        assertEquals(dummyMovies.releaseDate, data.releaseDate)
        assertNotNull(data.imagePath)
        assertEquals(dummyMovies.imagePath, data.imagePath)
    }

    @Test
    fun getListTvShow(){
        val dataSourceTv = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
       `when`(local.getTvShows()).thenReturn(dataSourceTv)
        dataRepository.getAllTvShows()

        val tvShow = Resource.success(PagedListUtil.mockPagedList(DataDummy.dummyTvShows()))
        Mockito.verify(local).getTvShows()
        assertNotNull(tvShow.data)
        assertEquals(tvShowResponse.size.toLong(), tvShow.data?.size?.toLong())
    }

    @Test
    fun getTvShowById(){
        val detailTv = MutableLiveData<TvShowEntity>()
        detailTv.value = dummyTvShow
        `when` (local.getDetailTvShow(dummyTvShow.tvShowId)).thenReturn(detailTv)

        val data = LiveDataTestUtil.getValue(dataRepository.getDetailTvShow(dummyTvShow.tvShowId))
        verify(local).getDetailMovie(dummyMovies.movieId)
        assertNotNull(data.title)
        assertEquals(dummyMovies.title, data.title)
        assertNotNull(data.description)
        assertEquals(dummyMovies.description, data.description)
        assertNotNull(data.releaseDate)
        assertEquals(dummyMovies.releaseDate, data.releaseDate)
        assertNotNull(data.imagePath)
        assertEquals(dummyMovies.imagePath, data.imagePath)
    }

}