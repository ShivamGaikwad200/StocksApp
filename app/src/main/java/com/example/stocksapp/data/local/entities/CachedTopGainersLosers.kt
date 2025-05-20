package com.example.stocksapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.stocksapp.data.local.converter.TopGainerLoserConverter
import com.example.stocksapp.data.remote.TopGainerLoser

@Entity(tableName = "cached_top_gainers_losers")
data class CachedTopGainersLosers(
    @PrimaryKey val id: Int = 1, // Single entry cache
    @TypeConverters(TopGainerLoserConverter::class) val data: TopGainerLoser,
    @ColumnInfo("last_updated")val lastUpdated: Long = System.currentTimeMillis()
)