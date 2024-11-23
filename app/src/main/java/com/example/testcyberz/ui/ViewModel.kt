package com.example.testcyberz.ui

import android.content.Context
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testcyberz.data.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import com.example.testcyberz.data.Event

class EventViewModel(private val eventRepository: EventRepository, private val context: Context) : ViewModel() {
    private val _events = MutableStateFlow<List<Event>>(emptyList())  // Состояние всех событий
    val events: StateFlow<List<Event>> = _events

    private val _filteredEvents = MutableStateFlow<List<Event>>(emptyList())  // Состояние фильтрованных событий
    val filteredEvents: StateFlow<List<Event>> = _filteredEvents

    var selectedType: String = "All"
    var selectedDate: Date? = null  // Хранение выбранной даты
    var maxDistance: Int = 20

    // Загрузка событий
    fun loadEvents() {
        viewModelScope.launch {
            val eventsFromRepo = eventRepository.getEventsFromCache()  // Получаем данные из репозитория
            _events.value = eventsFromRepo
            applyFilters()  // Применяем фильтры к загруженным событиям
        }
    }

    // Применение фильтров
    fun applyFilters() {
        val filtered = _events.value.filter { event ->
            filterByType(event) && filterByDate(event) && filterByDistance(event)
        }
        _filteredEvents.value = filtered  // Обновляем состояние фильтрованных событий
    }

    // Фильтрация по типу события
    private fun filterByType(event: Event): Boolean {
        return selectedType == "All" || event.type == selectedType
    }

    // Фильтрация по дате события
    private fun filterByDate(event: Event): Boolean {
        selectedDate?.let { date ->
            // Проверяем, соответствует ли дата события выбранной пользователем
            val eventDate = event.date // Убедитесь, что `Event.date` уже преобразован в объект `Date`
            return eventDate.after(date) || eventDate == date // Проверяем, что дата события после или равна выбранной
        }
        return true
    }

    private fun filterByDistance(event: Event): Boolean {
        val userLocation = Location("").apply {
            latitude = 0.0
            longitude = 0.0
        }

        val eventLocation = Location("").apply {
            latitude = event.latitude
            longitude = event.longitude
        }

        val distance = userLocation.distanceTo(eventLocation) / 1000 // В километрах
        return distance <= maxDistance
    }

    private suspend fun fetchEventsFromRemote(): List<Event> {
        val response = eventRepository.api.getEvents()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Failed to fetch events from API: ${response.code()}")
        }
    }
}
