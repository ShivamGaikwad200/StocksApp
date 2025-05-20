package com.example.stocksapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocksapp.data.remote.SymbolMatch
import com.example.stocksapp.domain.repository.RecentSearchesRepository
import com.example.stocksapp.domain.repository.SearchRepository
import com.example.stocksapp.presentation.common.BaseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository,
    private val recentSearchesRepository: RecentSearchesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<BaseModel<SearchUiState>>(BaseModel.Loading)
    val uiState: StateFlow<BaseModel<SearchUiState>> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadRecentSearches()
        setupSearchDebounce()
    }

    private fun loadRecentSearches() {
        viewModelScope.launch {
            _uiState.value = BaseModel.Loading
            try {
                val recentSearches = recentSearchesRepository.getRecentSearches()
                _uiState.value = BaseModel.Success(
                    SearchUiState(
                        searchResults = emptyList(),
                        recentSearches = recentSearches,
                        isLoading = false
                    )
                )
            } catch (e: Exception) {
                _uiState.value = BaseModel.Error(e.message ?: "Failed to load recent searches")
            }
        }
    }

    private fun setupSearchDebounce() {
        _searchQuery
            .debounce(300)
            .onEach { query ->
                if (query.isNotEmpty()) {
                    performSearch(query)
                }
            }
            .launchIn(viewModelScope)
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                when (currentState) {
                    is BaseModel.Success -> BaseModel.Success(
                        currentState.data.copy(
                            isLoading = true,
                            searchResults = emptyList()
                        )
                    )
                    else -> currentState
                }
            }

            try {
                searchRepository.searchTickers(query).collect { result ->
                    result.fold(
                        onSuccess = { searchTicker ->
                            val recentSearches = recentSearchesRepository.getRecentSearches()
                            _uiState.value = BaseModel.Success(
                                SearchUiState(
                                    searchResults = searchTicker.matches(),
                                    recentSearches = recentSearches,
                                    isLoading = false
                                )
                            )
                            recentSearchesRepository.addRecentSearch(query)
                        },
                        onFailure = { error ->
                            val recentSearches = recentSearchesRepository.getRecentSearches()
                            _uiState.value = BaseModel.Success(
                                SearchUiState(
                                    searchResults = emptyList(),
                                    recentSearches = recentSearches,
                                    isLoading = false,
                                    errorMessage = error.message ?: "Search failed"
                                )
                            )
                        }
                    )
                }
            } catch (e: Exception) {
                val recentSearches = recentSearchesRepository.getRecentSearches()
                _uiState.value = BaseModel.Success(
                    SearchUiState(
                        searchResults = emptyList(),
                        recentSearches = recentSearches,
                        isLoading = false,
                        errorMessage = e.message ?: "Search failed"
                    )
                )
            }
        }
    }
}

data class SearchUiState(
    val searchResults: List<SymbolMatch>,
    val recentSearches: Flow<List<String>>,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)