package id.herdroid.moviecatalog.api.source

import androidx.lifecycle.LiveData
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
import java.util.*

class DataRepository private constructor(
    private val remoteDataRepository: RemoteDataRepository,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : DataSource {

    companion object {
        @Volatile
        private var instance: DataRepository? = null

        fun getInstance(
            remoteDataRepository: RemoteDataRepository,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): DataRepository = instance ?: synchronized(this) {
            instance ?: DataRepository(remoteDataRepository, localDataSource, appExecutors)
        }
    }

    //MOVIE REPOSITORY
    override fun getAllMovies(page: Int): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<MovieEntity>>(appExecutors) {
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

    override fun getDetailMovie(id: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, List<MovieEntity>>(appExecutors){
            override fun loadFromDB(): LiveData<MovieEntity> {
                return   localDataSource.getDetailMovie(id)
            }

            override fun shouldFetch(data: MovieEntity?): Boolean {
                return  data == null
            }

            override fun createCall(): LiveData<ApiResponse<List<MovieEntity>>> {
                return remoteDataRepository.getMovies()
            }

            override fun saveCallResult(data: List<MovieEntity>?) {

            }

        }.asLiveData()
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
        appExecutors.diskIO().execute{
            localDataSource.setFavoriteMovie(movie, state)
        }
    }

    override fun removeFavoriteMovie(movie: MovieEntity) {
        localDataSource.removeFavoriteMovie(movie)
    }

    //TVSHOW REPOSITORY
    override fun getAllTvShows(page: Int): LiveData<Resource<PagedList<TvShowEntity>>> {
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

    override fun getDetailTvShow(id: Int): LiveData<Resource<TvShowEntity>> {
        return object : NetworkBoundResource<TvShowEntity, List<TvShowEntity>>(appExecutors){
            override fun loadFromDB(): LiveData<TvShowEntity> {
                return   localDataSource.getDetailTvShow(id)
            }

            override fun shouldFetch(data: TvShowEntity?): Boolean {
                return  data == null
            }

            override fun createCall(): LiveData<ApiResponse<List<TvShowEntity>>> {
                return remoteDataRepository.getTvShows()
            }

            override fun saveCallResult(data: List<TvShowEntity>?) {

            }

        }.asLiveData()
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
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteTvShow(tvShow, state)
        }
    }

    override fun removeFavoriteTvShow(tvShow: TvShowEntity) {
        return localDataSource.removeFavoriteTvShow(tvShow)
    }
}
