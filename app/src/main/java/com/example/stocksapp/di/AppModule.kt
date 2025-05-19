package com.example.stocksapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.stocksapp.data.local.StockDatabase
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "stock_preferences")

val AppModule = module {
    single<Context> { androidContext() }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>()) // API key interceptor from NetworkModule
            .addInterceptor(HttpLoggingInterceptor().apply {
                HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    single {
        Room.databaseBuilder(
            get(),
            StockDatabase::class.java,
            "stocks.db"
        ).build()
    }
}