package com.example.stocksapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.stocksapp.data.local.converter.StockChartDataConverter
import com.example.stocksapp.data.remote.StockChartData

@Entity(tableName = "cached_stock_chart")
data class CachedStockChartData(
    @PrimaryKey val symbol: String,
    @TypeConverters(StockChartDataConverter::class) val data: StockChartData,
    @ColumnInfo(name = "last_updated") val lastUpdated: Long = System.currentTimeMillis()
)