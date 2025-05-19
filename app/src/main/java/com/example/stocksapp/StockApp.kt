package com.example.stocksapp

import android.app.Application
import com.example.stocksapp.di.AppModule
import com.example.stocksapp.di.NetworkModule
import com.example.stocksapp.di.RepositoryModule
import com.example.stocksapp.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class StockApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@StockApp)
            modules(AppModule, NetworkModule, RepositoryModule, ViewModelModule)
        }
    }
}