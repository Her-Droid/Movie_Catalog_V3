package id.herdroid.moviecatalog.api.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import id.herdroid.moviecatalog.api.NetworkBoundResource
import id.herdroid.moviecatalog.api.remote.RemoteDataRepository
import id.herdroid.moviecatalog.data.entity.MovieEntity
import id.herdroid.moviecatalog.data.entity.TvShowEntity
import id.herdroid.moviecatalog.database.LocalDataSource
import id.herdroid.moviecatalog.utils.AppExecutors
import id.herdroid.moviecatalog.vo.ApiResponse
import id.herdroid.moviecatalog.vo.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList

class FakeDataRepository(
    private val remoteDataRepository: RemoteDataRepository,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : DataSource {

    //MOVIE REPOSITORY
    override fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MovieEntity>, List<MovieEntity>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder().apply {
                    setEnablePlaceholders(false)
                    setInitialLoadSizeHint(4)
                    setPageSize(4)
                }.build()
                return LivePagedListBuilder(localDataSource.getMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()


            public override fun createCall(): LiveData<ApiResponse<List<MovieEntity>>> =
                remoteDataRepository.getMovies()

            override fun saveCallResult(data: List<MovieEntity>?) {
                val movieList = ArrayList<MovieEntity>()
                if (data != null) {
                    for (item in data) {
                        val movie = MovieEntity(
                            item.movieId,
                            item.title,
                            item.description,
                            item.imagePath,
                            item.releaseDate,
                            false
                        )
                        movieList.add(movie)
                    }
                }

                localDataSource.insertMovie(movieList)
            }

        }.asLiveData()
    }

    override fun getDetailMovie(id: Int): LiveData<MovieEntity> {
        return localDataSource.getDetailMovie(id)
    }

    override fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder().apply {
            setEnablePlaceholders(false)
            setInitialLoadSizeHint(4)
            setPageSize(4)
        }.build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovie(), config).build()
    }

    override fun setFavoriteMovie(movie: MovieEntity, state: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.setFavoriteMovie(movie)
        }
    }

    override fun removeFavoriteMovie(movie: MovieEntity) {
        localDataSource.removeFavoriteMovie(movie)
    }

    //TVSHOW REPOSITORY
    override fun getAllTvShows(): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object :
            NetworkBoundResource<PagedList<TvShowEntity>, List<TvShowEntity>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder().apply {
                    setEnablePlaceholders(false)
                    setInitialLoadSizeHint(4)
                    setPageSize(4)
                }.build()
                return LivePagedListBuilder(localDataSource.getTvShows(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<TvShowEntity>>> =
                remoteDataRepository.getTvShows()


            override fun saveCallResult(data: List<TvShowEntity>?) {
                val tvShowList = ArrayList<TvShowEntity>()
                if (data != null) {
                    for (item in data) {
                        val tvShow = TvShowEntity(
                            item.tvShowId,
                            item.title,
                            item.description,
                            item.imagePath,
                            item.releaseDate,
                            false
                        )
                        tvShowList.add(tvShow)
                    }
                }

                localDataSource.insertTvShow(tvShowList)
            }

        }.asLiveData()
    }

    override fun getDetailTvShow(id: Int): LiveData<TvShowEntity> {
        return localDataSource.getDetailTvShow(id)
    }

    override fun getFavoriteTvShows(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder().apply {
            setEnablePlaceholders(false)
            setInitialLoadSizeHint(4)
            setPageSize(4)
        }.build()
        return LivePagedListBuilder(localDataSource.getFavoriteTvShow(), config).build()
    }

    override fun setFavoriteTvShow(tvShow: TvShowEntity, state: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.setFavoriteTvShow(tvShow)
        }
    }

    override fun removeFavoriteTvShow(tvShow: TvShowEntity) {
        return localDataSource.removeFavoriteTvShow(tvShow)
    }
}
