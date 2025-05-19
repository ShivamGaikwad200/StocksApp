package com.example.stocksapp.di

import com.example.stocksapp.data.remote.api.StockAPI
import okhttp3.Interceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val NetworkModule = module {

    val apikey = "BKGQ12CWDXK5MI2N"
    val baseurl = "https://www.alphavantage.co/"

    single<Interceptor> {
        Interceptor { chain ->
            val original = chain.request()
            val url = original.url.newBuilder()
                .addQueryParameter("apikey", apikey)
                .build()
            val request = original.newBuilder().url(url).build()
            chain.proceed(request)
        }
    }

    single {
        Retrofit.Builder()
            .baseUrl(baseurl)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<StockAPI> {
        get<Retrofit>().create(StockAPI::class.java)
    }
}
