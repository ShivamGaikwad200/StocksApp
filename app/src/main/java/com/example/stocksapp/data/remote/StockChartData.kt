package com.example.stocksapp.data.remote

data class StockChartData(
    val timestamp: Long,
    val price: Double,
    val volume: Long
)