package id.herdroid.moviecatalog.data.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "TvShow")
@Parcelize
data class TvShowEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val tvShowId: Int = 0,

    @ColumnInfo(name = "original_name")
    @SerializedName("original_name")
    val title: String? = null,

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    val description: String? = null,

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    val imagePath: String? = null,

    @ColumnInfo(name = "first_air_date")
    @SerializedName("first_air_date")
    val releaseDate: String? = null,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false

) : Parcelable