package com.example.stocksapp.domain.repository

import com.example.stocksapp.data.remote.SearchTicker
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchTickers(query: String): Flow<Result<SearchTicker>>
}