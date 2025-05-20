package com.example.stocksapp.presentation.explore

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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
    viewModel: ExploreViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Stock Explorer") },
                actions = {
                    IconButton(onClick = { /* Handle search */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            when (state) {
                is BaseModel.Loading -> LoadingIndicator()
                is BaseModel.Error -> ErrorView((state as BaseModel.Error).error)
                is BaseModel.Success -> {
                    val data = (state as BaseModel.Success<ExploreUiState>).data
                    if (data.recentSearches.isNotEmpty()) {
                        SectionHeader(
                            title = "Recently Viewed",
                            onViewAll = { /* Optional */ }
                        )
                        StockGrid(
                            items = data.topGainers,
                            onItemClick = onStockSelected
                        )
                    }


                    SectionHeader(
                        title = "Top Gainers",
                        onViewAll = { onViewAll("gainers") }
                    )
                    StockGrid(
                        items = data.topGainers,
                        onItemClick = onStockSelected
                    )

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
    onItemClick: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier.padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) { item ->
            StockCard(item = item, onClick = { onItemClick(item.ticker) })
        }
    }
}

@Composable
fun StockCard(item: Item, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = item.ticker,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = item.price,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.changePercentage,
                color = if (item.changePercentage.startsWith("-")) Color.Red else Color.Green,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}