package com.appintuitions.rvkotlin.repo

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.appintuitions.rvkotlin.viewmodel.models.Movie
import androidx.room.Room


@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): MovieDao?

    companion object{
        private lateinit var INSTANCE: AppDatabase
        private val DBNAME  = "movie-database"

        fun getAppDatabase(context: Application): AppDatabase {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DBNAME
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE.close()
        }
    }

}