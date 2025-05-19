package com.example.stocksapp.data.repository

import com.example.stocksapp.data.local.StockDAO
import com.example.stocksapp.data.local.entities.CachedCompanyOverview
import com.example.stocksapp.data.local.entities.CachedStockChartData
import com.example.stocksapp.data.remote.CompanyOverview
import com.example.stocksapp.data.remote.StockChartData
import com.example.stocksapp.data.remote.api.StockAPI
import com.example.stocksapp.domain.repository.CompanyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CompanyRepositoryImpl(
    private val stockApi: StockAPI,
    private val stockDao: StockDAO
) : CompanyRepository {

    private companion object {
        const val TIME = 24 * 60 * 60 * 1000
    }

    override fun getCompanyOverview(symbol: String): Flow<Result<CompanyOverview>> = flow {
        val expiryTime = System.currentTimeMillis() - TIME

        try {
            val freshData = stockApi.getCompanyOverview(symbol)
            stockDao.insertCompanyOverview(
                CachedCompanyOverview(symbol = symbol, data = freshData)
            )
            emit(Result.success(freshData))
        } catch (e: Exception) {
            stockDao.getValidCompanyOverview(symbol, expiryTime)?.let {
                emit(Result.success(it.data))
            } ?: emit(Result.failure(e))
        }
    }

    override suspend fun getStockChartData(symbol: String): Result<StockChartData> {
        val expiryTime = System.currentTimeMillis() - TIME

        return try {
            // Try network first
            val response = stockApi.getStockChartData(symbol)
            stockDao.insertStockChartData(
                CachedStockChartData(
                    symbol = symbol,
                    data = response,
                    lastUpdated = System.currentTimeMillis()
                )
            )
            Result.success(response)
        } catch (e: Exception) {
            // Fallback to cache if available
            stockDao.getValidStockChartData(symbol, expiryTime)?.let {
                Result.success(it.data)
            } ?: Result.failure(Exception("Failed to fetch chart data: ${e.message}"))
        }
    }
}