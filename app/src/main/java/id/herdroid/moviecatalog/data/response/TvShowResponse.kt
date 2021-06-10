package id.herdroid.moviecatalog.data.response

import com.google.gson.annotations.SerializedName
import id.herdroid.moviecatalog.data.entity.TvShowEntity

data class TvShowResponse(
    @SerializedName("page")
    val page: Int,

    @SerializedName("total_pages")
    val totalPage: Int,

    @SerializedName("results")
    val results: List<TvShowEntity>
)



