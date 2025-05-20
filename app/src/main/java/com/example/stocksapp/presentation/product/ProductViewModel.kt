package com.example.stocksapp.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocksapp.data.remote.CompanyOverview
import com.example.stocksapp.data.remote.StockChartData
import com.example.stocksapp.domain.repository.CompanyRepository
import com.example.stocksapp.presentation.common.BaseModel
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

    private val _companyInfoState = MutableStateFlow<BaseModel<CompanyOverview>>(BaseModel.Loading)
    val companyInfoState: StateFlow<BaseModel<CompanyOverview>> = _companyInfoState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.value = BaseModel.Loading
            _companyInfoState.value = BaseModel.Loading

            try {

                val chartData = companyRepository.getStockChartData(symbol).getOrThrow()

                companyRepository.getCompanyOverview(symbol).collect { result ->
                    _companyInfoState.value = result.fold(
                        onSuccess = { data -> BaseModel.Success(data) },
                        onFailure = { e -> BaseModel.Error(e.message ?: "Company info load failed") }
                    )
                }

                _uiState.value = BaseModel.Success(
                    ProductUiState(
                        chartData = chartData
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
    val chartData: StockChartData
)