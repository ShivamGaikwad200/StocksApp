package com.example.stocksapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.stocksapp.data.local.converters.TickerSearchConverter
import com.example.stocksapp.data.remote.SearchTicker

@Entity(tableName = "cached_ticker_search")
data class CachedTickerSearch(
    @PrimaryKey val query: String,
    @TypeConverters(TickerSearchConverter::class) val data: SearchTicker,
    @ColumnInfo("last_updated") val lastUpdated: Long = System.currentTimeMillis()
)