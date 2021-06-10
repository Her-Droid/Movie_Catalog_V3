package id.herdroid.moviecatalog.data.response

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import id.herdroid.moviecatalog.data.entity.MovieEntity

data class MovieResponse(

    @SerializedName("page")
    val page: Int,

    @SerializedName("total_pages")
    val totalPage: Int,

    @SerializedName("results")
    val results: List<MovieEntity>

)


