package com.example.stocksapp.data.repository

import com.example.stocksapp.data.local.StockDAO
import com.example.stocksapp.data.local.entities.CachedTickerSearch
import com.example.stocksapp.data.remote.SearchTicker
import com.example.stocksapp.data.remote.api.StockAPI
import com.example.stocksapp.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRepositoryImpl(
    private val stockApi: StockAPI,
    private val stockDao: StockDAO
) : SearchRepository {

    private companion object {
        const val CACHE_EXPIRY_MS = 24 * 60 * 60 * 1000 // 24 hours for search results
    }

    override fun searchTickers(query: String): Flow<Result<SearchTicker>> = flow {
        val expiryTime = System.currentTimeMillis() - CACHE_EXPIRY_MS

        try {
            val freshData = stockApi.searchTickers(query=query)
            stockDao.insertSearchResults(
                CachedTickerSearch(query = query, data = freshData)
            )
            emit(Result.success(freshData))
        } catch (e: Exception) {
            stockDao.getValidSearchResults(query, expiryTime)?.let {
                emit(Result.success(it.data))
            } ?: emit(Result.failure(e))
        }
    }
}