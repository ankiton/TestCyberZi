package com.example.testcyberz.ui

import android.location.Location
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testcyberz.data.Event
import com.example.testcyberz.databinding.ItemEventBinding
import java.text.SimpleDateFormat
import java.util.*

class EventAdapter(private val onClick: (Event) -> Unit) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private val events = mutableListOf<Event>()

    fun submitList(newEvents: List<Event>) {
        events.clear()
        events.addAll(newEvents)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
    }

    override fun getItemCount(): Int = events.size

    inner class EventViewHolder(
        private val binding: ItemEventBinding,
        private val onClick: (Event) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())

        fun bind(event: Event) {
            binding.eventName.text = event.name
            binding.eventType.text = event.type
            binding.eventDate.text = dateFormat.format(event.date)

            if (event.latitude != 0.0 && event.longitude != 0.0) {
                val userLocation = Location("").apply {
                    latitude = 0.0
                    longitude = 0.0
                }

                val eventLocation = Location("").apply {
                    latitude = event.latitude
                    longitude = event.longitude
                }

                val distance = userLocation.distanceTo(eventLocation) / 1000
                binding.eventDistance.text = String.format("%.2f km", distance)
            } else {
                binding.eventDistance.text = "Distance not available"
            }

            binding.root.setOnClickListener {
                onClick(event)
            }
        }
    }
}
