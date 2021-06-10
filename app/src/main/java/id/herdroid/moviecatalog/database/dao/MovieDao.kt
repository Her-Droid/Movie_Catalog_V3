package id.herdroid.moviecatalog.database.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import id.herdroid.moviecatalog.data.entity.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie")
    fun getMovieDb(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM Movie WHERE id = :movieId")
    fun getMovieDbById(movieId: Int?): LiveData<MovieEntity>

    @Query("SELECT * FROM Movie WHERE favorite = 1")
    fun getFavoriteMovie() : DataSource.Factory<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = MovieEntity::class)
    fun insertMovie(movie: List<MovieEntity>)

    @Update(entity = MovieEntity::class)
    fun updateMovie(movie : MovieEntity)

    @Delete
    fun delete(movie: MovieEntity)
}