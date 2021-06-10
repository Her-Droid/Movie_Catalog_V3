package id.herdroid.moviecatalog.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import id.herdroid.moviecatalog.api.source.DataRepository
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

class TvShowViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: TvShowViewModel
    private var dataRepository = mock(DataRepository::class.java)

    @Mock
    lateinit var observer: Observer<List<TvShowEntity>>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = TvShowViewModel(dataRepository)
    }


    @Test
    fun getTvShows(){
        val dummyTvShow = DataDummy.dummyTvShows()
        val dataTvShow = MutableLiveData<List<TvShowEntity>>()
        dataTvShow.value = dummyTvShow

        `when`(dataRepository.getListTvShow()).thenReturn(dataTvShow)
        val tvShow = viewModel.loadTvShow().value
        verify(dataRepository).getListTvShow()

        assertNotNull(tvShow)
        assertEquals(10, tvShow?.size)

        viewModel.loadTvShow().observeForever(observer)
        verify(observer).onChanged(dummyTvShow)
    }
}