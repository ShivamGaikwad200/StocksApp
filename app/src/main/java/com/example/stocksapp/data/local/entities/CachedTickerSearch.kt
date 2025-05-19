package com.example.stocksapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.stocksapp.data.remote.SearchTicker

@Entity(tableName = "cached_ticker_search")
data class CachedTickerSearch(
    @PrimaryKey val query: String,
    val data: SearchTicker,
    val lastUpdated: Long = System.currentTimeMillis()
)