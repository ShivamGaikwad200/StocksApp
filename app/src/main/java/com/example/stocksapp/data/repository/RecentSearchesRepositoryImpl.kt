package com.example.stocksapp.data.repository


import com.example.stocksapp.data.local.StockDAO
import com.example.stocksapp.data.local.entities.RecentSearch
import com.example.stocksapp.domain.repository.RecentSearchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecentSearchesRepositoryImpl(
    private val stockDao: StockDAO
) : RecentSearchesRepository {

    private companion object {
        const val MAX_RECENT_SEARCHES = 3
    }

    override suspend fun getRecentSearches(): Flow<List<String>> {
        return stockDao.getRecentSearches(MAX_RECENT_SEARCHES)
            .map { searches -> searches.map {
                it.query
            } }
    }

    override suspend fun addRecentSearch(query: String) {
        stockDao.deleteRecentSearch(query)
        stockDao.insertRecentSearch(RecentSearch(query))

        val expiryTime = System.currentTimeMillis() - (24 * 60 * 60 * 1000)
        stockDao.deleteExpiredRecentSearches(expiryTime)
    }
}