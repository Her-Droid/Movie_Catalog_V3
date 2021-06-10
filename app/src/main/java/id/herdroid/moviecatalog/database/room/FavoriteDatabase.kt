package id.herdroid.moviecatalog.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.herdroid.moviecatalog.data.entity.MovieEntity
import id.herdroid.moviecatalog.data.entity.TvShowEntity
import id.herdroid.moviecatalog.database.dao.MovieDao
import id.herdroid.moviecatalog.database.dao.TvShowDao

@Database(entities = [MovieEntity::class, TvShowEntity::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase(){

    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao

    companion object{
        private const val DATABASE_NAME = "MOVIE_CATALOG"
        private var databaseInstance: FavoriteDatabase? = null

        fun getDatabase(context: Context): FavoriteDatabase {
            if (databaseInstance == null){
                databaseInstance = Room
                        .databaseBuilder(context.applicationContext,
                                FavoriteDatabase::class.java,
                            DATABASE_NAME
                        )
                        .allowMainThreadQueries()
                        .build()
            }
            return databaseInstance as FavoriteDatabase
        }
    }
}   