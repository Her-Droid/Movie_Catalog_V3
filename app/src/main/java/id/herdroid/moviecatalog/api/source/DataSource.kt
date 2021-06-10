package id.herdroid.moviecatalog.api.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import id.herdroid.moviecatalog.data.entity.MovieEntity
import id.herdroid.moviecatalog.data.entity.TvShowEntity
import id.herdroid.moviecatalog.vo.Resource

interface DataSource {

    //Movie List
    fun getAllMovies(page: Int): LiveData<Resource<PagedList<MovieEntity>>>

    fun getDetailMovie(id: Int): LiveData<Resource<MovieEntity>>

    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>>

    fun setFavoriteMovie(movie: MovieEntity, state: Boolean)

    fun removeFavoriteMovie(movie: MovieEntity)



    //Tv Show List
    fun getAllTvShows(page: Int): LiveData<Resource<PagedList<TvShowEntity>>>

    fun getDetailTvShow(id: Int): LiveData<Resource<TvShowEntity>>

    fun getFavoriteTvShows(): LiveData<PagedList<TvShowEntity>>

    fun setFavoriteTvShow(tvShow: TvShowEntity, state: Boolean)

    fun removeFavoriteTvShow(tvShow: TvShowEntity)

}