package com.example.stocksapp.di

import com.example.stocksapp.data.local.StockDatabase
import com.example.stocksapp.data.repository.CompanyRepositoryImpl
import com.example.stocksapp.data.repository.RecentSearchesRepositoryImpl
import com.example.stocksapp.data.repository.SearchRepositoryImpl
import com.example.stocksapp.data.repository.TopGainersLosersRepositoryImpl
import com.example.stocksapp.domain.repository.CompanyRepository
import com.example.stocksapp.domain.repository.RecentSearchesRepository
import com.example.stocksapp.domain.repository.SearchRepository
import com.example.stocksapp.domain.repository.TopGainersLosersRepository
import org.koin.dsl.module

val RepositoryModule = module {
    single { StockDatabase.getDatabase(get()).stockDao() }

    // Repositories
    single<TopGainersLosersRepository> {
        TopGainersLosersRepositoryImpl(
            stockApi = get(),
            stockDao = get()
        )
    }

    single<CompanyRepository> {
        CompanyRepositoryImpl(
            stockApi = get(),
            stockDao = get()
        )
    }

    single<SearchRepository> {
        SearchRepositoryImpl(
            stockApi = get(),
            stockDao = get()
        )
    }

    single<RecentSearchesRepository> {
        RecentSearchesRepositoryImpl(
            stockDao = get()
        )
    }
}