package com.myspotify.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myspotify.data.local.dao.SongDao
import com.myspotify.data.local.entity.SongDB

@Database(entities = [SongDB::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun songDao(): SongDao
}