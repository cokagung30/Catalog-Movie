package com.example.submission3.sqlite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.submission3.sqlite.dao.MovieDao
import com.example.submission3.sqlite.model.MovieFavorite

@Database(entities = [MovieFavorite::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        private lateinit var instance: AppDatabase
        fun getInstance(): AppDatabase = instance
        fun createDatabase(context: Context) {
            instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "MovieDb")
                .allowMainThreadQueries()
                .build()
        }
    }

}