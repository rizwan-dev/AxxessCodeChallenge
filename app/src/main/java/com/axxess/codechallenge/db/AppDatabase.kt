package com.axxess.codechallenge.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.axxess.codechallenge.db.dao.CommentsDao
import com.axxess.codechallenge.db.model.Comments


@Database(entities = [Comments::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun commentsDao(): CommentsDao


    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "axxess.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}