# 📈 Stocks App

An Android application developed in **Kotlin** that allows users to explore and track stock and ETF performance. It includes search functionality, trending market sections, detailed product views, and seamless navigation.

---

## 🚀 Tech Stack

- **Kotlin** – Core language for Android development  
- **Jetpack Compose** – Modern UI toolkit  
- **Material 3** – UI theming and components  
- **Retrofit** – For API calls  
- **Koin** – Dependency Injection   
- **Coroutines & Flow** – Asynchronous programming  
- **MPAndroidChart** – For line chart rendering  
- **Room** – For caching and persistence  

---

## 📱 Features

- 📊 **Explore Screen**
  - Recently searched items
  - Top Gainers and Top Losers
  - Grid of cards displaying stock/ETF information

- 🔍 **Search Screen**
  - Search bar with dropdown suggestions
  - Saves recent search history

- 📈 **Product Screen**
  - Basic stock/ETF info
  - Line chart showing price trends
  - Data fetched via Alpha Vantage APIs

- 🗂️ **View All Screen**
  - Paginated list of stocks under each section
  - Scroll to load more

---

## 🧠 App Architecture

- **MVVM pattern** for clean separation of concerns  
- **Caching Layer** with optional expiry for performance  

---

## 🔗 API Integration

Powered by [Alpha Vantage APIs](https://www.alphavantage.co):

- 🔎 **Ticker Search**
- 📈 **Fundamentals / Overview**
- 📉 **Top Gainers and Losers (Alpha Intelligence)**

---
