package de.critequal.mobile.composefeedreader.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FeedURLDao {
    @Query("SELECT * from feedurl")
    fun getAll(): List<FeedURL>

    @Query("SELECT * FROM feedurl WHERE uid IN (:feedURLId)")
    fun loadByIds(feedURLId: Int): FeedURL

    @Insert
    fun insertAll(vararg feedURLs: FeedURL)

    @Delete
    fun delete(feedURL: FeedURL)

}