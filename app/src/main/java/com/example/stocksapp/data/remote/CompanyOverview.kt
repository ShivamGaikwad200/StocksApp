package com.example.stocksapp.data.remote

import com.google.gson.annotations.SerializedName

data class CompanyOverview(
    @SerializedName("Symbol")
    val symbol: String,

    @SerializedName("AssetType")
    val assetType: String,

    @SerializedName("Name")
    val name: String,

    @SerializedName("Description")
    val description: String,

    @SerializedName("CIK")
    val cik: String,

    @SerializedName("Exchange")
    val exchange: String,

    @SerializedName("Currency")
    val currency: String,

    @SerializedName("Country")
    val country: String,

    @SerializedName("Sector")
    val sector: String,

    @SerializedName("Industry")
    val industry: String,

    @SerializedName("Address")
    val address: String,

    @SerializedName("OfficialSite")
    val officialSite: String,

    @SerializedName("FiscalYearEnd")
    val fiscalYearEnd: String,

    @SerializedName("LatestQuarter")
    val latestQuarter: String,

    @SerializedName("MarketCapitalization")
    val marketCap: String,

    @SerializedName("EBITDA")
    val ebitda: String,

    @SerializedName("PERatio")
    val peRatio: Double? = null,

    @SerializedName("PEGRatio")
    val pegRatio: Double? = null,

    @SerializedName("BookValue")
    val bookValue: Double? = null,

    @SerializedName("DividendPerShare")
    val dividendPerShare: Double? = null,

    @SerializedName("DividendYield")
    val dividendYield: Double? = null,

    @SerializedName("EPS")
    val eps: Double? = null,

    @SerializedName("RevenuePerShareTTM")
    val revenuePerShareTTM: Double? = null,

    @SerializedName("ProfitMargin")
    val profitMargin: Double? = null,

    @SerializedName("OperatingMarginTTM")
    val operatingMarginTTM: Double? = null,

    @SerializedName("ReturnOnAssetsTTM")
    val returnOnAssetsTTM: Double? = null,

    @SerializedName("ReturnOnEquityTTM")
    val returnOnEquityTTM: Double? = null,

    @SerializedName("RevenueTTM")
    val revenueTTM: String,

    @SerializedName("GrossProfitTTM")
    val grossProfitTTM: String,

    @SerializedName("DilutedEPSTTM")
    val dilutedEpsTTM: Double? = null,

    @SerializedName("QuarterlyEarningsGrowthYOY")
    val quarterlyEarningsGrowthYOY: Double? = null,

    @SerializedName("QuarterlyRevenueGrowthYOY")
    val quarterlyRevenueGrowthYOY: Double? = null,

    @SerializedName("AnalystTargetPrice")
    val analystTargetPrice: Double? = null,

    @SerializedName("AnalystRatingStrongBuy")
    val analystRatingStrongBuy: Int? = null,

    @SerializedName("AnalystRatingBuy")
    val analystRatingBuy: Int? = null,

    @SerializedName("AnalystRatingHold")
    val analystRatingHold: Int? = null,

    @SerializedName("AnalystRatingSell")
    val analystRatingSell: Int? = null,

    @SerializedName("AnalystRatingStrongSell")
    val analystRatingStrongSell: Int? = null,

    @SerializedName("TrailingPE")
    val trailingPE: Double? = null,

    @SerializedName("ForwardPE")
    val forwardPE: Double? = null,

    @SerializedName("PriceToSalesRatioTTM")
    val priceToSalesRatioTTM: Double? = null,

    @SerializedName("PriceToBookRatio")
    val priceToBookRatio: Double? = null,

    @SerializedName("EVToRevenue")
    val evToRevenue: Double? = null,

    @SerializedName("EVToEBITDA")
    val evToEbitda: Double? = null,

    @SerializedName("Beta")
    val beta: Double? = null,

    @SerializedName("52WeekHigh")
    val week52High: Double? = null,

    @SerializedName("52WeekLow")
    val week52Low: Double? = null,

    @SerializedName("50DayMovingAverage")
    val day50MovingAvg: Double? = null,

    @SerializedName("200DayMovingAverage")
    val day200MovingAvg: Double? = null,

    @SerializedName("SharesOutstanding")
    val sharesOutstanding: String,

    @SerializedName("DividendDate")
    val dividendDate: String? = null,

    @SerializedName("ExDividendDate")
    val exDividendDate: String? = null
)

