package com.example.stocksapp.di

import com.example.stocksapp.data.remote.api.StockAPI
import okhttp3.Interceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val NetworkModule = module {

    private const val API_KEY = "BKGQ12CWDXK5MI2N"
    private const val BASE_URL = "https://www.alphavantage.co/"

    single<Interceptor> {
        Interceptor { chain ->
            val original = chain.request()
            val url = original.url.newBuilder()
                .addQueryParameter("apikey", API_KEY)
                .build()
            val request = original.newBuilder().url(url).build()
            chain.proceed(request)
        }
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<StockAPI> {
        get<Retrofit>().create(StockAPI::class.java)
    }
}
