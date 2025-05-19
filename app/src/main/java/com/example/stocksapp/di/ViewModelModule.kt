package com.example.stocksapp.di

import com.example.stocksapp.presentation.explore.ExploreViewModel
import com.example.stocksapp.presentation.product.ProductViewModel
import com.example.stocksapp.presentation.search.SearchViewModel
import com.example.stocksapp.presentation.viewall.ViewAllViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    // Explore Screen
    viewModel {
        ExploreViewModel(
            topGainersLosersRepository = get(),
            recentSearchesRepository = get()
        )
    }

    // Product Detail Screen
    viewModel { (symbol: String) ->
        ProductViewModel(
            symbol = symbol,
            companyRepository = get()
        )
    }

    // Search Screen
    viewModel {
        SearchViewModel(
            searchRepository = get(),
            recentSearchesRepository = get()
        )
    }

    // View All Screen
    viewModel { (section: String) ->
        ViewAllViewModel(
            section = section,
            topGainersLosersRepository = get()
        )
    }
}