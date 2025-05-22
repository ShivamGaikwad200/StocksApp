package com.example.stocksapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_stocks")
data class RecentStocks(
    @PrimaryKey
    val symbol: String,
    val timestamp: Long = System.currentTimeMillis()
)