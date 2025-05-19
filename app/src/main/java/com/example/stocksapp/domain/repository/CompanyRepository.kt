package com.example.stocksapp.domain.repository

import com.example.stocksapp.data.remote.CompanyOverview
import com.example.stocksapp.data.remote.StockChartData
import kotlinx.coroutines.flow.Flow

interface CompanyRepository {
    fun getCompanyOverview(symbol: String): Flow<Result<CompanyOverview>>
    suspend fun getStockChartData(symbol: String): Result<List<StockChartData>>
}
