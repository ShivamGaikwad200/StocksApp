package com.example.stocksapp.data.remote.api

import com.example.stocksapp.data.remote.CompanyOverview
import com.example.stocksapp.data.remote.SearchTicker
import com.example.stocksapp.data.remote.StockChartData
import com.example.stocksapp.data.remote.TopGainerLoser
import retrofit2.http.GET
import retrofit2.http.Query

const val APIKEY = "BKGQ12CWDXK5MI2N"

interface StockAPI {
    @GET("query") // Base URL handles the rest
    suspend fun getTopGainersLosers(
        @Query("function") function: String = "TOP_GAINERS_LOSERS",
        @Query("apikey") apiKey: String = APIKEY
    ): TopGainerLoser

    @GET("query")
    suspend fun getCompanyOverview(
        @Query("function") function: String = "OVERVIEW",
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = APIKEY
    ): CompanyOverview

    @GET("query")
    suspend fun searchTickers(
        @Query("function") function: String = "SYMBOL_SEARCH",
        @Query("keywords") query: String,
        @Query("apikey") apiKey: String = APIKEY
    ): SearchTicker

    @GET("query")
    suspend fun getStockChartData(
        @Query("function") function: String = "TIME_SERIES_DAILY",
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = APIKEY,
    ): StockChartData
}