package com.example.stocksapp.data.remote

import com.google.gson.annotations.SerializedName

data class TopGainerLoser(
    @SerializedName("metadata")
    val metadata: String,

    @SerializedName("last_updated")
    val lastUpdated: String,  // Could use DateTime with custom parser if needed

    @SerializedName("top_gainers")
    val topGainers: List<Item>,

    @SerializedName("top_losers")
    val topLosers: List<Item>
)

data class Item(
    @SerializedName("ticker")
    val ticker: String,

    @SerializedName("price")
    val price: String,

    @SerializedName("change_amount")
    val changeAmount: String,

    @SerializedName("change_percentage")
    val changePercentage: String,

    @SerializedName("volume")
    val volume: String
)
