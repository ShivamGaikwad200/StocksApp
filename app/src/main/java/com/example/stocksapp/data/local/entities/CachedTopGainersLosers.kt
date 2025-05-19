package com.example.stocksapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.stocksapp.data.remote.TopGainerLoser

@Entity(tableName = "cached_top_gainers_losers")
data class CachedTopGainersLosers(
    @PrimaryKey val id: Int = 1, // Single entry cache
    val data: TopGainerLoser,
    val lastUpdated: Long = System.currentTimeMillis()
)