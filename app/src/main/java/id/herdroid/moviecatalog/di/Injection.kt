package id.herdroid.moviecatalog.di

import android.content.Context
import id.herdroid.moviecatalog.api.network.ApiClient
import id.herdroid.moviecatalog.api.network.ApiService
import id.herdroid.moviecatalog.api.remote.RemoteDataRepository
import id.herdroid.moviecatalog.api.source.DataRepository
import id.herdroid.moviecatalog.database.LocalDataSource
import id.herdroid.moviecatalog.database.room.FavoriteDatabase
import id.herdroid.moviecatalog.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): DataRepository {

        val database = FavoriteDatabase.getDatabase(context)

        val remoteDataSource = RemoteDataRepository.getInstance(
                ApiClient().getApi().create(ApiService::class.java)
        )
        val localDataSource = LocalDataSource.getInstance(database.movieDao(), database.tvShowDao())
        val appExecutors = AppExecutors()

        return DataRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}