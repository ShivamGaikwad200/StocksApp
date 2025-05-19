package com.example.stocksapp.presentation.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocksapp.data.remote.Item
import com.example.stocksapp.domain.repository.RecentSearchesRepository
import com.example.stocksapp.domain.repository.TopGainersLosersRepository
import com.example.stocksapp.presentation.common.BaseModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val topGainersLosersRepository: TopGainersLosersRepository,
    private val recentSearchesRepository: RecentSearchesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<BaseModel<ExploreUiState>>(BaseModel.Loading)
    val uiState: StateFlow<BaseModel<ExploreUiState>> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.value = BaseModel.Loading

            try {
                combine(
                    topGainersLosersRepository.getTopGainersLosers(),
                    recentSearchesRepository.getRecentSearches()
                ) { gainersLosersResult, recentSearches ->
                    gainersLosersResult.fold(
                        onSuccess = { data ->
                            BaseModel.Success(
                                ExploreUiState(
                                    topGainers = data.topGainers,
                                    topLosers = data.topLosers,
                                    recentSearches = recentSearches
                                )
                            )
                        },
                        onFailure = { error ->
                            BaseModel.Error(error.message ?: "Unknown error")
                        }
                    )
                }.collect { state ->
                    _uiState.value = state
                }
            } catch (e: Exception) {
                _uiState.value = BaseModel.Error(e.message ?: "Failed to load data")
            }
        }
    }

    fun addRecentSearch(query: String) {
        viewModelScope.launch {
            recentSearchesRepository.addRecentSearch(query)
        }
    }
}

data class ExploreUiState(
    val topGainers: List<Item>,
    val topLosers: List<Item>,
    val recentSearches: List<String>
)