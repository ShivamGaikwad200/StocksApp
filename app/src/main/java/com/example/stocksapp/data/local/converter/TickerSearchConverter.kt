package com.example.stocksapp.data.local.converters

import androidx.room.TypeConverter
import com.example.stocksapp.data.remote.SearchTicker
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TickerSearchConverter {
    private val gson = Gson()
    private val type = object : TypeToken<SearchTicker>() {}.type

    @TypeConverter
    fun fromSearchTicker(searchTicker: SearchTicker?): String? {
        return gson.toJson(searchTicker, type)
    }

    @TypeConverter
    fun toSearchTicker(json: String?): SearchTicker? {
        return gson.fromJson(json, type)
    }
}