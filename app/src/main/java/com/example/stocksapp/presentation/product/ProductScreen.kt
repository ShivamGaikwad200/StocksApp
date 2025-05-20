package com.example.stocksapp.presentation.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.stocksapp.data.remote.CompanyOverview
import com.example.stocksapp.data.remote.StockChartData
import com.example.stocksapp.presentation.common.BaseModel
import com.example.stocksapp.presentation.common.ErrorView
import com.example.stocksapp.presentation.common.LoadingIndicator
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    symbol: String,
    onBack: () -> Unit,
    viewModel: ProductViewModel = koinViewModel(parameters = { parametersOf(symbol) })
) {
    val state by viewModel.uiState.collectAsState()
    val companyInfoState by viewModel.companyInfoState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(symbol) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        when (state) {
            is BaseModel.Loading -> LoadingIndicator()
            is BaseModel.Error -> ErrorView((state as BaseModel.Error).error)
            is BaseModel.Success -> {
                val data = (state as BaseModel.Success<ProductUiState>).data
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    when (companyInfoState) {
                        is BaseModel.Success -> {
                            CompanyOverviewSection((companyInfoState as BaseModel.Success<CompanyOverview>).data)
                        }
                        is BaseModel.Error -> {
                            ErrorView((companyInfoState as BaseModel.Error).error)
                        }
                        is BaseModel.Loading -> {
                            LoadingIndicator()
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    StockChartSection(data.chartData)

                    Spacer(modifier = Modifier.height(16.dp))

                    if (companyInfoState is BaseModel.Success) {
                        KeyMetricsSection((companyInfoState as BaseModel.Success<CompanyOverview>).data)
                    }
                }
            }
        }
    }
}

@Composable
private fun CompanyOverviewSection(data: CompanyOverview) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = data.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = data.sector,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = data.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun StockChartSection(data: StockChartData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Price History (Last 30 Days)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.LightGray.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text("Stock Price Chart")
            }
        }
    }
}

@Composable
private fun KeyMetricsSection(data: CompanyOverview) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Key Metrics",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            KeyValueRow("Market Cap", data.marketCap)
            KeyValueRow("P/E Ratio", data.peRatio?.toString() ?: "N/A")
            KeyValueRow("52W High", data.week52High?.toString() ?: "N/A")
            KeyValueRow("52W Low", data.week52Low?.toString() ?: "N/A")
            KeyValueRow("Dividend Yield", data.dividendYield?.let { "${it}%" } ?: "N/A")
        }
    }
}

@Composable
private fun KeyValueRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}