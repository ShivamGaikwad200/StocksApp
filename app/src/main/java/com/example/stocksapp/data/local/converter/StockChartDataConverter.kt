package com.example.stocksapp.data.local.converter

import androidx.room.TypeConverter
import com.example.stocksapp.data.remote.StockChartData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StockChartDataConverter {
    private val gson = Gson()
    private val type = object : TypeToken<StockChartData>() {}.type

    @TypeConverter
    fun fromStockChartData(stockChartData: StockChartData?): String? {
        return gson.toJson(stockChartData, type)
    }

    @TypeConverter
    fun toStockChartData(json: String?): StockChartData? {
        return gson.fromJson(json, type)
    }
}