package id.herdroid.moviecatalog.viewmodel

import android.graphics.Movie
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import id.herdroid.moviecatalog.api.source.DataRepository
import id.herdroid.moviecatalog.data.entity.MovieEntity
import id.herdroid.moviecatalog.data.entity.TvShowEntity
import id.herdroid.moviecatalog.vo.Resource

class DetailViewModel(private val dataRepository: DataRepository) :ViewModel() {
    private var movId : Int = 0

    fun setSelectedData(courseId: Int) {
        this.movId = courseId
    }

    fun getMovie(): LiveData<Resource<MovieEntity>> = dataRepository.getDetailMovie(movId)


    fun setFavoriteMovie(movieEntity: MovieEntity){
        val favorite = !movieEntity.favorite
        dataRepository.setFavoriteMovie(movieEntity, favorite)
    }


    fun getTvShow(): LiveData<Resource<TvShowEntity>> = dataRepository.getDetailTvShow(movId)

    fun setFavoriteTvShow(tvShowEntity: TvShowEntity){
        if (tvShowEntity != null){
            val favorite = tvShowEntity.favorite
            dataRepository.setFavoriteTvShow(tvShowEntity, favorite)
        }
    }


}
