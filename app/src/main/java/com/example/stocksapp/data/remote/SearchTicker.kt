package com.example.stocksapp.data.remote

import com.google.gson.annotations.SerializedName

data class SearchTicker(
    @SerializedName("bestMatches")
    val bestMatches: List<SymbolMatch>
) {
    fun matches(): List<SymbolMatch> {
        TODO("Not yet implemented")
    }
}

data class SymbolMatch(
    @SerializedName("1. symbol")
    val symbol: String,

    @SerializedName("2. name")
    val name: String,

    @SerializedName("3. type")
    val type: String,

    @SerializedName("4. region")
    val region: String,

    @SerializedName("5. marketOpen")
    val marketOpen: String,

    @SerializedName("6. marketClose")
    val marketClose: String,

    @SerializedName("7. timezone")
    val timezone: String,

    @SerializedName("8. currency")
    val currency: String,

    @SerializedName("9. matchScore")
    val matchScore: String
)