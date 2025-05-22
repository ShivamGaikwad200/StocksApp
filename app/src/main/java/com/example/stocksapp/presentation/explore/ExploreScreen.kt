package com.example.stocksapp.presentation.explore

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.stocksapp.R
import com.example.stocksapp.data.remote.Item
import com.example.stocksapp.presentation.common.BaseModel
import com.example.stocksapp.presentation.common.ErrorView
import com.example.stocksapp.presentation.common.LoadingIndicator
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    onStockSelected: (String) -> Unit,
    onViewAll: (String) -> Unit,
    onSearchClicked: () -> Unit,
    viewModel: ExploreViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Stock Explorer") },
                actions = {
                    IconButton(onClick = onSearchClicked) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        }
    ) { padding ->
        // Remove the Column with verticalScroll and use Box with LazyColumn
        Box(modifier = Modifier.padding(padding)) {
            when (state) {
                is BaseModel.Loading -> LoadingIndicator()
                is BaseModel.Error -> ErrorView((state as BaseModel.Error).error)
                is BaseModel.Success -> {
                    val data = (state as BaseModel.Success<ExploreUiState>).data
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Recent Searches Section
                        item {
                            if (data.recentSearches.isNotEmpty()) {
                                Column {
                                    SectionHeader(
                                        title = "Recently Viewed",
                                        onViewAll = { onViewAll("recent") }
                                    )
                                    RecentStocksList(
                                        symbols = data.recentSearches,
                                        onStockClick = onStockSelected
                                    )
                                }
                            }
                        }

                        // Top Gainers Section
                        item {
                            Column {
                                SectionHeader(
                                    title = "Top Gainers",
                                    onViewAll = { onViewAll("gainers") }
                                )
                                StockGrid(
                                    items = data.topGainers,
                                    onItemClick = onStockSelected
                                )
                            }
                        }

                        // Top Losers Section
                        item {
                            Column {
                                SectionHeader(
                                    title = "Top Losers",
                                    onViewAll = { onViewAll("losers") }
                                )
                                StockGrid(
                                    items = data.topLosers,
                                    onItemClick = onStockSelected
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecentStocksList(
    symbols: List<String>,
    onStockClick: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(symbols) { symbol ->
            StockChip(
                symbol = symbol,
                onClick = { onStockClick(symbol) }
            )
        }
    }
}

@Composable
fun StockChip(
    symbol: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Text(
            text = symbol,
            modifier = Modifier.padding(12.dp),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
private fun SectionHeader(title: String, onViewAll: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        TextButton(onClick = onViewAll) {
            Text("View All")
        }
    }
}

@Composable
private fun StockGrid(
    items: List<Item>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val rows = items.chunked(2)

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(rows) { rowItems ->
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { item ->
                    StockCard(
                        item = item,
                        onClick = { onItemClick(item.ticker) }
                    )
                }
            }
        }
    }
}

@Composable
fun StockCard(
    item: Item,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .aspectRatio(1f) // Make square
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Stock Logo (from drawables)
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Get logo resource ID based on ticker
                val logoResId = remember(item.ticker) {
                    getLogoResourceId(item.ticker)
                }

                if (logoResId != null) {
                    Image(
                        painter = painterResource(id = logoResId),
                        contentDescription = "${item.ticker} logo",
                        modifier = Modifier.size(48.dp),
                        contentScale = ContentScale.Fit
                    )
                } else {
                    // Fallback icon if no logo found
                    Icon(
                        imageVector = Icons.Default.Business,
                        contentDescription = "Stock icon",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // Stock Name
            Text(
                text = item.ticker,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Stock Price
            Text(
                text = "$${item.price}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )

            // Price Change (smaller and subtle)
            Text(
                text = item.changePercentage,
                color = if (item.changePercentage.startsWith("-")) Color.Red else Color.Green,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

// Helper function to get logo resource ID
@DrawableRes
private fun getLogoResourceId(ticker: String): Int? {
    return when (ticker.uppercase()) {
        "AAPL" -> R.drawable.img
        // Add more mappings as needed
        else -> R.drawable.img
    }
}