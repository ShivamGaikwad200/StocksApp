package com.example.stocksapp.data.local

import androidx.room.*
import com.example.stocksapp.data.local.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopGainersLosers(data: CachedTopGainersLosers)

    @Query("SELECT * FROM cached_top_gainers_losers WHERE last_updated > :expiryTime LIMIT 1")
    suspend fun getValidTopGainersLosers(expiryTime: Long): CachedTopGainersLosers?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyOverview(data: CachedCompanyOverview)

    @Query("SELECT * FROM cached_company_overview WHERE symbol = :symbol AND last_updated > :expiryTime")
    suspend fun getValidCompanyOverview(symbol: String, expiryTime: Long): CachedCompanyOverview?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchResults(data: CachedTickerSearch)

    @Query("SELECT * FROM cached_ticker_search WHERE query = :query AND last_updated > :expiryTime")
    suspend fun getValidSearchResults(query: String, expiryTime: Long): CachedTickerSearch?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentSearch(search: RecentSearch)

    @Query("SELECT * FROM recent_searches ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecentSearches(limit: Int = 5): Flow<List<RecentSearch>>

    @Query("DELETE FROM recent_searches WHERE query = :query")
    suspend fun deleteRecentSearch(query: String)

    @Query("DELETE FROM recent_searches WHERE timestamp < :expiryTime")
    suspend fun deleteExpiredRecentSearches(expiryTime: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStockChartData(data: CachedStockChartData)

    @Query("SELECT * FROM cached_stock_chart WHERE symbol = :symbol AND last_updated > :expiryTime")
    suspend fun getValidStockChartData(symbol: String, expiryTime: Long): CachedStockChartData?
}