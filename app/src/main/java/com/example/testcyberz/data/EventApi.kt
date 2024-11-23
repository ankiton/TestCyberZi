package com.example.testcyberz.data


import retrofit2.Response
import retrofit2.http.GET

interface EventApi {
    @GET("mock_events.json")
    suspend fun getEvents(): Response<List<Event>>
}
