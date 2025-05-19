package com.example.stocksapp.data.remote.api

import com.example.stocksapp.data.remote.CompanyOverview
import com.example.stocksapp.data.remote.SearchTicker
import com.example.stocksapp.data.remote.TopGainerLoser
import retrofit2.http.GET
import retrofit2.http.Query

interface StockAPI{
    @GET("query?function=TOP_GAINERS_LOSERS")
    suspend fun getTopGainersLosers(): TopGainerLoser

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyOverview(
        @Query("symbol") symbol: String
    ): CompanyOverview

    @GET("query?function=SYMBOL_SEARCH")
    suspend fun searchTickers(
        @Query("keywords") query: String
    ): SearchTicker
}