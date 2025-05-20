package com.example.stocksapp.data.local.converter

import androidx.room.TypeConverter
import com.example.stocksapp.data.remote.CompanyOverview
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CompanyOverviewConverter {
    private val gson = Gson()
    private val type = object : TypeToken<CompanyOverview>() {}.type

    @TypeConverter
    fun fromCompanyOverview(companyOverview: CompanyOverview?): String? {
        return gson.toJson(companyOverview, type)
    }

    @TypeConverter
    fun toCompanyOverview(json: String?): CompanyOverview? {
        return gson.fromJson(json, type)
    }
}