package id.herdroid.moviecatalog.api.remote


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.herdroid.moviecatalog.api.network.ApiService
import id.herdroid.moviecatalog.data.entity.MovieEntity
import id.herdroid.moviecatalog.data.entity.TvShowEntity
import id.herdroid.moviecatalog.data.response.MovieResponse
import id.herdroid.moviecatalog.data.response.TvShowResponse
import id.herdroid.moviecatalog.utils.EspressoIdlingResource
import id.herdroid.moviecatalog.vo.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataRepository private constructor(private val apiService: ApiService) {

    companion object {
        @Volatile
        private var instance: RemoteDataRepository? = null

        fun getInstance(apiService: ApiService): RemoteDataRepository =
            instance ?: synchronized(this) {
                instance ?: RemoteDataRepository(apiService)
            }
    }

    fun getMovies(): LiveData<ApiResponse<List<MovieEntity>>> {
        EspressoIdlingResource.increment()
        val liveData = MutableLiveData<ApiResponse<List<MovieEntity>>>()
        apiService.getMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                    EspressoIdlingResource.decrement()
                }
                liveData.postValue(ApiResponse.success(response.body()!!.results))
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                liveData.postValue(ApiResponse.error(t.message.toString(), null))
            }
        })
        return liveData
    }

    fun getTvShows(): LiveData<ApiResponse<List<TvShowEntity>>> {
        EspressoIdlingResource.increment()
        val liveData = MutableLiveData<ApiResponse<List<TvShowEntity>>>()
        apiService.getTvShows().enqueue(object: Callback<TvShowResponse>{
            override fun onResponse(
                call: Call<TvShowResponse>,
                response: Response<TvShowResponse>
            ) {
                if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                    EspressoIdlingResource.decrement()
                }
                liveData.postValue(ApiResponse.success(response.body()!!.results))
            }

            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                liveData.postValue(ApiResponse.error(t.message.toString(), null))
            }
        })

        return liveData
    }


}