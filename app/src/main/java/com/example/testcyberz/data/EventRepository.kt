package com.example.testcyberz.data

import android.content.Context
import androidx.room.Room
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventRepository(context: Context) {

    private val database = Room.databaseBuilder(
        context.applicationContext,
        EventDatabase::class.java,
        "event_database"
    ).build()

    private val eventDao = database.eventDao()


    val api: EventApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.base.url/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EventApi::class.java)
    }

    suspend fun getEventsFromCache() = eventDao.getAllEvents()

    suspend fun saveEventsToCache(events: List<Event>) {
        eventDao.clearEvents()
        eventDao.insertEvents(events)
    }

    suspend fun isCacheExpired(): Boolean {
        val events = getEventsFromCache()
        if (events.isEmpty()) return true
        val lastUpdated = events.first().lastUpdated
        return (System.currentTimeMillis() - lastUpdated) > 30 * 60 * 1000
    }
}
