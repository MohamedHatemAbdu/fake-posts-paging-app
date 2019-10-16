package com.me.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.me.domain.entities.PostData

@Database(entities = [PostData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getPostsDao(): PostDao
}