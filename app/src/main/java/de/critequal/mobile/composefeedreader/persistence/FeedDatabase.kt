package de.critequal.mobile.composefeedreader.persistence

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FeedURL::class], version = 1)
abstract class FeedDatabase : RoomDatabase() {
    abstract fun feedURLDao(): FeedURLDao
}