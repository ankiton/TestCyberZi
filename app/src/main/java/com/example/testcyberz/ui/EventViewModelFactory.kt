package com.example.testcyberz.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testcyberz.data.EventRepository

class EventViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val eventRepository = EventRepository(context)
        if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            return EventViewModel(eventRepository, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
