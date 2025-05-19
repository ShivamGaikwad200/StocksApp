package com.example.stocksapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "recent_searches")
data class RecentSearch(
    @PrimaryKey
    val query: String,
    val timestamp: Long = System.currentTimeMillis()
)