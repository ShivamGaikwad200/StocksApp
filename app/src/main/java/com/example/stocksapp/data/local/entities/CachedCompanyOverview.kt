package com.example.stocksapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.stocksapp.data.remote.CompanyOverview

@Entity(tableName = "cached_company_overview")
data class CachedCompanyOverview(
    @PrimaryKey val symbol: String,
    val data: CompanyOverview,
    val lastUpdated: Long = System.currentTimeMillis()
)