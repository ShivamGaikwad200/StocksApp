package com.example.stocksapp.data.repository

import com.example.stocksapp.data.local.StockDAO
import com.example.stocksapp.data.local.entities.CachedTopGainersLosers
import com.example.stocksapp.data.remote.TopGainerLoser
import com.example.stocksapp.data.remote.api.StockAPI
import com.example.stocksapp.domain.repository.TopGainersLosersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TopGainersLosersRepositoryImpl (
    private val stockApi: StockAPI,
    private val stockDao: StockDAO
) : TopGainersLosersRepository {

    private companion object {
        const val CACHE_EXPIRY_MS = 24 * 60 * 60 * 1000 // 24 hour
    }

    override fun getTopGainersLosers(): Flow<Result<TopGainerLoser>> = flow {
        val expiryTime = System.currentTimeMillis() - CACHE_EXPIRY_MS

        try {
            val freshData = stockApi.getTopGainersLosers()
            stockDao.insertTopGainersLosers(
                CachedTopGainersLosers(
                    data = freshData,
                    lastUpdated = System.currentTimeMillis()
                )
            )
            emit(Result.success(freshData))
        } catch (e: Exception) {
            stockDao.getValidTopGainersLosers(expiryTime)?.let {
                emit(Result.success(it.data))
            } ?: emit(Result.failure(e))
        }
    }
}