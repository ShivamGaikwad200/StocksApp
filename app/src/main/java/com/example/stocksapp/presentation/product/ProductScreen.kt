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
import androidx.compose.foundation.layout.width
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
                title = { Text(text = symbol) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        when (state) {
            is BaseModel.Loading -> LoadingIndicator()
            is BaseModel.Error -> ErrorView(message = (state as BaseModel.Error).error)
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
                            val companyData = (companyInfoState as BaseModel.Success<CompanyOverview>).data
                            StockHeaderSection(companyData = companyData)
                            Spacer(modifier = Modifier.height(16.dp))
                            PriceChartSection(data = data.chartData)
                            Spacer(modifier = Modifier.height(16.dp))
                            KeyMetricsSection(data = companyData)
                            Spacer(modifier = Modifier.height(16.dp))
                            AboutCompanySection(data = companyData)
                        }
                        is BaseModel.Error -> {
                            ErrorView(message = (companyInfoState as BaseModel.Error).error)
                        }
                        is BaseModel.Loading -> {
                            LoadingIndicator()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StockHeaderSection(companyData: CompanyOverview) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = companyData.name,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(
                    text = companyData.symbol,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = " • ",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = companyData.exchange,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = "$${companyData.week52High ?: "--"}",
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "+0.41%", // This should come from chart data
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Green,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun PriceChartSection(data: StockChartData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.LightGray.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Stock Price Chart")
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("1W", "1M", "3M", "6M", "1Y").forEach { period ->
                    Text(
                        text = period,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(8.dp)
                    )
                }
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
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Key Metrics",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MetricItem(label = "52-Week High", value = data.week52High ?: "--")
                MetricItem(label = "Current Price", value = data.week52High ?: "--")
                MetricItem(label = "52-Week Low", value = data.week52Low ?: "--")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MetricItem(label = "Market Cap", value = data.marketCap ?: "--")
                MetricItem(label = "P/E Ratio", value = data.peRatio ?: "--")
                MetricItem(label = "Beta", value = data.beta ?: "--")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MetricItem(
                    label = "Dividend Yield",
                    value = data.dividendYield?.let { "${it}%" } ?: "--"
                )
                MetricItem(label = "Profit Margin", value = data.profitMargin ?: "--")
                MetricItem(label = "", value = "")
            }
        }
    }
}

@Composable
private fun MetricItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun AboutCompanySection(data: CompanyOverview) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "About ${data.name}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = data.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Industry: ${data.industry}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Sector: ${data.sector}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}