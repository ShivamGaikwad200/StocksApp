package com.example.stocksapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.stocksapp.data.local.converter.CompanyOverviewConverter
import com.example.stocksapp.data.remote.CompanyOverview

@Entity(tableName = "cached_company_overview")
data class CachedCompanyOverview(
    @PrimaryKey val symbol: String,
    @TypeConverters(CompanyOverviewConverter::class) val data: CompanyOverview,
    @ColumnInfo("last_updated")val lastUpdated: Long = System.currentTimeMillis()
)