package com.example.stocksapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface RecentSearchesRepository {
    suspend fun getRecentSearches(): Flow<List<String>>

    suspend fun addRecentSearch(query: String)


}