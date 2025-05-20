package com.example.stocksapp.data.local.converter

import androidx.room.TypeConverter
import com.example.stocksapp.data.remote.TopGainerLoser
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TopGainerLoserConverter {
    private val gson = Gson()
    private val type = object : TypeToken<TopGainerLoser>() {}.type

    @TypeConverter
    fun fromTopGainerLoser(topGainerLoser: TopGainerLoser?): String? {
        return gson.toJson(topGainerLoser, type)
    }

    @TypeConverter
    fun toTopGainerLoser(json: String?): TopGainerLoser? {
        return gson.fromJson(json, type)
    }
}