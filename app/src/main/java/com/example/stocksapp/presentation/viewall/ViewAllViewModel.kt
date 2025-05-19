package com.example.stocksapp.presentation.viewall

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocksapp.data.remote.Item
import com.example.stocksapp.domain.repository.TopGainersLosersRepository
import com.example.stocksapp.presentation.common.BaseModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.Locale

class ViewAllViewModel(
    private val section: String,
    private val topGainersLosersRepository: TopGainersLosersRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<BaseModel<ViewAllUiState>>(BaseModel.Loading)
    val uiState: StateFlow<BaseModel<ViewAllUiState>> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            topGainersLosersRepository.getTopGainersLosers()
                .onStart { _uiState.value = BaseModel.Loading }
                .catch { e ->
                    _uiState.value = BaseModel.Error(
                        e.message ?: "Failed to load $section data"
                    )
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { data ->
                            val items = when (section.lowercase(Locale.ROOT)) {
                                "gainers" -> data.topGainers
                                "losers" -> data.topLosers
                                else -> throw IllegalArgumentException("Invalid section: $section")
                            }
                            _uiState.value = BaseModel.Success(
                                ViewAllUiState(
                                    items = items,
                                    sectionTitle = section.replaceFirstChar {
                                        if (it.isLowerCase()) it.titlecase(Locale.ROOT)
                                        else it.toString()
                                    }
                                )
                            )
                        },
                        onFailure = { error ->
                            _uiState.value = BaseModel.Error(
                                error.message ?: "Failed to load $section data"
                            )
                        }
                    )
                }
        }
    }
}

data class ViewAllUiState(
    val items: List<Item>,
    val sectionTitle: String
)