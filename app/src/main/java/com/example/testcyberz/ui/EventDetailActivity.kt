package com.example.testcyberz.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.testcyberz.R
import com.example.testcyberz.data.Event
import android.provider.CalendarContract

class EventDetailActivity : AppCompatActivity() {

    private lateinit var event: Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        event = intent.getSerializableExtra("EVENT") as Event

        findViewById<TextView>(R.id.eventTitle).text = event.name
        findViewById<TextView>(R.id.eventDescription).text = event.description
        findViewById<TextView>(R.id.eventDate).text = event.date.toString()
        findViewById<TextView>(R.id.eventLocation).text = "Location: ${event.latitude}, ${event.longitude}"

        findViewById<Button>(R.id.addToCalendarButton).setOnClickListener {
            addToCalendar(event)
        }
    }

    private fun addToCalendar(event: Event) {
        val intent = Intent(Intent.ACTION_INSERT)
        intent.type = "vnd.android.cursor.item/event"
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, event.date.time)
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, event.date.time + 60 * 60 * 1000) // Добавляем 1 час
        intent.putExtra(CalendarContract.Events.TITLE, event.name)
        intent.putExtra(CalendarContract.Events.DESCRIPTION, event.description)
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "${event.latitude}, ${event.longitude}")
        startActivity(intent)
    }
}
