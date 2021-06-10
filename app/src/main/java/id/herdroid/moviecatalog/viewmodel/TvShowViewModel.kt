package id.herdroid.moviecatalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import id.herdroid.moviecatalog.api.source.DataRepository
import id.herdroid.moviecatalog.data.entity.TvShowEntity
import id.herdroid.moviecatalog.vo.Resource

class TvShowViewModel( private val dataRepository: DataRepository) : ViewModel() {
    var page = 1

    fun loadTvShow(page:Int): LiveData<Resource<PagedList<TvShowEntity>>> = dataRepository.getAllTvShows(page)

    fun getMovieFavorite(): LiveData<PagedList<TvShowEntity>> = dataRepository.getFavoriteTvShows()
}
