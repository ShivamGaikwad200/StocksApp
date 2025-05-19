package com.example.stocksapp.domain.repository


import com.example.stocksapp.data.remote.TopGainerLoser
import kotlinx.coroutines.flow.Flow

interface TopGainersLosersRepository {
    fun getTopGainersLosers(): Flow<Result<TopGainerLoser>>
}