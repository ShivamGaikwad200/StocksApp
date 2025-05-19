package com.example.stocksapp.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocksapp.data.remote.CompanyOverview
import com.example.stocksapp.data.remote.StockChartData
import com.example.stocksapp.domain.repository.CompanyRepository
import com.example.stocksapp.presentation.common.BaseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val symbol: String,
    private val companyRepository: CompanyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<BaseModel<ProductUiState>>(BaseModel.Loading)
    val uiState: StateFlow<BaseModel<ProductUiState>> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.value = BaseModel.Loading

            try {
                val companyInfo = companyRepository.getCompanyOverview(symbol)
                val chartData = companyRepository.getStockChartData(symbol)

                _uiState.value = BaseModel.Success(
                    ProductUiState(
                        companyInfo = companyInfo,
                        chartData = chartData.getOrThrow()
                    )
                )
            } catch (e: Exception) {
                _uiState.value = BaseModel.Error(e.message ?: "Failed to load data")
            }
        }
    }

    fun refreshData() {
        loadData()
    }
}

data class ProductUiState(
    val companyInfo: Flow<Result<CompanyOverview>>,
    val chartData: StockChartData
)