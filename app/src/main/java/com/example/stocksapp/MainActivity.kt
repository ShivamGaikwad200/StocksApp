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
import com.example.stocksapp.Screen.Companion.arguments
import com.example.stocksapp.Screen.Companion.routeWithArgs
import com.example.stocksapp.Screen.Product.STOCK_SYMBOL
import com.example.stocksapp.Screen.ViewAll.SECTION
import com.example.stocksapp.presentation.explore.ExploreScreen
import com.example.stocksapp.presentation.product.ProductScreen
import com.example.stocksapp.ui.theme.StocksAppTheme
import com.example.stocksapp.presentation.viewall.ViewAllScreen
import org.koin.android.ext.android.inject

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
        composable(route = Screen.Explore.route) {
            ExploreScreen(
                onStockSelected = { symbol ->
                    navController.navigate(Screen.Product.createRoute(symbol))
                },
                onViewAll = { section ->
                    navController.navigate(Screen.ViewAll.createRoute(section))
                }
            )
        }
        composable(
            route = Screen.Product.routeWithArgs,
            arguments = Screen.Product.arguments
        ) { navBackStackEntry ->
            val symbol = navBackStackEntry.arguments?.getString(STOCK_SYMBOL) ?: ""
            ProductScreen(
                symbol = symbol,
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.ViewAll.routeWithArgs,
            arguments = Screen.ViewAll.arguments
        ) { navBackStackEntry ->
            val section = navBackStackEntry.arguments?.getString(SECTION) ?: ""
            ViewAllScreen(
                section = section,
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

    companion object {
        val Product.routeWithArgs: String get() = Product.route
        val Product.arguments: List<NamedNavArgument> get() = listOf(
            navArgument(Product.STOCK_SYMBOL) { type = NavType.StringType }
        )

        val ViewAll.routeWithArgs: String get() = ViewAll.route
        val ViewAll.arguments: List<NamedNavArgument> get() = listOf(
            navArgument(ViewAll.SECTION) { type = NavType.StringType }
        )
    }
}