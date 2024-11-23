package com.example.testcyberz

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testcyberz.databinding.ActivityMainBinding
import com.example.testcyberz.ui.EventAdapter
import com.example.testcyberz.ui.EventDetailActivity
import com.example.testcyberz.ui.EventViewModel
import com.example.testcyberz.ui.EventViewModelFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = EventViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory).get(EventViewModel::class.java)

        val myButton = findViewById<Button>(R.id.dateFilterButton)
        myButton.setOnClickListener {
            openDatePicker()
        }

        val adapter = EventAdapter { event ->
            val intent = Intent(this, EventDetailActivity::class.java)
            intent.putExtra("EVENT", event)
            startActivity(intent)
        }
        binding.eventsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.eventsRecyclerView.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModel.events.collect { events ->
                if (events.isEmpty()) {
                    showNoEventsMessage()
                }
                adapter.submitList(events)
            }
        }

        viewModel.loadEvents()
    }

    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDay)
                }.time
                viewModel.selectedDate = selectedDate
                viewModel.applyFilters()
            },
            year,
            month,
            dayOfMonth
        )
        datePickerDialog.show()
    }

    private fun showNoEventsMessage() {
        Toast.makeText(this, "No events available at the moment", Toast.LENGTH_SHORT).show()
    }
}
