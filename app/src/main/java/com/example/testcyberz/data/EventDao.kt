package com.example.testcyberz.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EventDao {

    @Query("SELECT * FROM event")
    suspend fun getAllEvents(): List<Event>

    @Query("DELETE FROM event")
    suspend fun clearEvents(): Int

    @Insert
    suspend fun insertEvents(events: List<Event>)
}


