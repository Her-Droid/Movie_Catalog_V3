package id.herdroid.moviecatalog.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import id.herdroid.moviecatalog.data.entity.MovieEntity
import id.herdroid.moviecatalog.data.entity.TvShowEntity
import id.herdroid.moviecatalog.database.dao.MovieDao
import id.herdroid.moviecatalog.database.dao.TvShowDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LocalDataSource private constructor(
    private val movieDao: MovieDao,
    private val tvShowDao: TvShowDao
) {


    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao, tvShowDao: TvShowDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieDao, tvShowDao)
    }

    // Movie Dao
    fun getMovies(): DataSource.Factory<Int, MovieEntity> = movieDao.getMovieDb()

    fun getDetailMovie(movieId: Int): LiveData<MovieEntity> = movieDao.getMovieDbById(movieId)

    fun getFavoriteMovie(): DataSource.Factory<Int, MovieEntity> = movieDao.getFavoriteMovie()

    fun insertMovie(movieEntity: List<MovieEntity>) = movieDao.insertMovie(movieEntity)

    fun setFavoriteMovie(movieEntity: MovieEntity, state: Boolean) {
        movieEntity.favorite = state
        movieDao.updateMovie(movieEntity)
    }

    fun removeFavoriteMovie(movie: MovieEntity) {
        GlobalScope.launch(Dispatchers.Main) { movieDao.delete(movie) }
    }

    //Tv Show Dao
    fun getTvShows(): DataSource.Factory<Int, TvShowEntity> = tvShowDao.getTvShowDb()

    fun getDetailTvShow(tvShowId: Int): LiveData<TvShowEntity> = tvShowDao.getTvShowDbById(tvShowId)

    fun getFavoriteTvShow(): DataSource.Factory<Int, TvShowEntity> = tvShowDao.getFavoriteTvShow()

    fun insertTvShow(tvShowEntity: List<TvShowEntity>) = tvShowDao.insertTvShow(tvShowEntity)

    fun setFavoriteTvShow(tvShowEntity: TvShowEntity, state: Boolean) {
        tvShowEntity.favorite = state
        tvShowDao.updateTvShow(tvShowEntity)
    }

    fun removeFavoriteTvShow(tvShow: TvShowEntity) {
        GlobalScope.launch(Dispatchers.Main) { tvShowDao.delete(tvShow) }
    }

}