package id.herdroid.moviecatalog.database.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import id.herdroid.moviecatalog.data.entity.TvShowEntity

@Dao
interface TvShowDao {

    @Query("SELECT * FROM TvShow")
    fun getTvShowDb(): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM TvShow WHERE id = :tvShowId")
    fun getTvShowDbById(tvShowId: Int?): LiveData<TvShowEntity>

    @Query("SELECT * FROM TvShow WHERE favorite = 1")
    fun getFavoriteTvShow() : DataSource.Factory<Int, TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = TvShowEntity::class)
    fun insertTvShow(tvShow: List<TvShowEntity>)

    @Update(entity = TvShowEntity::class)
    fun updateTvShow(tvShow : TvShowEntity)

    @Delete
    fun delete(tvShow: TvShowEntity)

}