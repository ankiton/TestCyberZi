package com.example.testcyberz.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@TypeConverters(Converters::class)
@Entity(tableName = "event")
data class Event(
    @PrimaryKey (autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String,
    val type: String,
    val date: Date,
    val latitude: Double,
    val longitude: Double,
    val lastUpdated: Long
) : Parcelable
