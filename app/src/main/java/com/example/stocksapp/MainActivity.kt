package com.example.stocksapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.stocksapp.Screen.Companion.argument
import com.example.stocksapp.Screen.Companion.arguments
import com.example.stocksapp.Screen.Companion.routeWithArg
import com.example.stocksapp.Screen.Companion.routeWithArgs
import com.example.stocksapp.presentation.explore.ExploreScreen
import com.example.stocksapp.presentation.product.ProductScreen
import com.example.stocksapp.presentation.search.SearchScreen
import com.example.stocksapp.ui.theme.StocksAppTheme
import com.example.stocksapp.presentation.viewall.ViewAllScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StocksAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StockTrackerApp()
                }
            }
        }
    }
}

@Composable
fun StockTrackerApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Explore.route
    ) {
        composable(Screen.Explore.route) {
            ExploreScreen(
                onStockSelected = { symbol ->
                    navController.navigate(Screen.Product.createRoute(symbol))
                },
                onViewAll = { section ->
                    navController.navigate(Screen.ViewAll.createRoute(section))
                },
                onSearchClicked = {
                    navController.navigate(Screen.Search.route)
                }
            )
        }

        composable(
            route = routeWithArgs,
            arguments = arguments
        ) { backStackEntry ->
            val symbol = backStackEntry.arguments?.getString("symbol") ?: ""
            ProductScreen(
                symbol = symbol,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.ViewAll.routeWithArg,
            arguments = Screen.ViewAll.argument
        ) { backStackEntry ->
            val section = backStackEntry.arguments?.getString(Screen.ViewAll.SECTION) ?: ""
            ViewAllScreen(
                section = section,
                onStockSelected = { symbol ->
                    navController.navigate(Screen.Product.createRoute(symbol))
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(route = Screen.Search.route) {
            SearchScreen(
                onStockSelected = { symbol ->
                    navController.navigate(Screen.Product.createRoute(symbol))
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}

sealed class Screen(val route: String) {
    data object Explore : Screen("explore")

    data object Product : Screen("product/{symbol}") {
        const val STOCK_SYMBOL = "symbol"
        fun createRoute(symbol: String) = "product/$symbol"
    }

    data object ViewAll : Screen("view_all/{section}") {
        const val SECTION = "section"
        fun createRoute(section: String) = "view_all/$section"
    }

    data object Search : Screen("search")

    companion object {
        val routeWithArgs: String get() = Product.route
        val arguments: List<NamedNavArgument> get() = listOf(
            navArgument(Product.STOCK_SYMBOL) { type = NavType.StringType }
        )

        val ViewAll.routeWithArg: String get() = ViewAll.route
        val ViewAll.argument: List<NamedNavArgument> get() = listOf(
            navArgument(ViewAll.SECTION) { type = NavType.StringType }
        )
    }
}