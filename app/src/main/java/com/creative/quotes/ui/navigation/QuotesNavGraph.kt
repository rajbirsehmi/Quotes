package com.creative.quotes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.creative.quotes.ui.screens.AllQuotationsScreen
import com.creative.quotes.ui.screens.AllSubjectsScreen
import com.creative.quotes.ui.screens.EmptyQuotationsScreen
import com.creative.quotes.ui.screens.QuotationContent
import com.creative.quotes.ui.screens.QuotesBySubjectScreen
import com.creative.quotes.ui.viewmodel.QuotesViewModel

@Composable
fun QuotesNavGraph(
    navController: NavHostController = rememberNavController(),
    viewModel: QuotesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val startDestination = if (uiState.subjects.isEmpty()) {
        Screen.EmptyQuotationsScreen.route
    } else {
        Screen.AllSubjectsScreen.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.AllQuotationsScreen.route) {
            AllQuotationsScreen(
                onQuoteClick = { quoteId ->
                    navController.navigate(Screen.Quotation.createRoute(quoteId))
                }
            )
        }
        composable(
            route = "subject_quotes/{subjectName}", // {subjectName} is the placeholder
            arguments = listOf(navArgument("subjectName") { type = NavType.StringType })
        ) { backStackEntry ->
            val subjectName = backStackEntry.arguments?.getString("subjectName")
            QuotesBySubjectScreen(
                subject = subjectName ?: "",
                viewModel = viewModel,
                onBackCLick = { navController.navigateUp() },
                onQuoteClick = { quoteId ->
                    navController.navigate(Screen.Quotation.createRoute(quoteId))
                }
            )
        }
        composable(route = Screen.AllSubjectsScreen.route) {
            AllSubjectsScreen(
                onSubjectClick = { subjectName ->
                    navController.navigate("subject_quotes/$subjectName")
                }
            )
        }
        composable(route = Screen.EmptyQuotationsScreen.route) {
            EmptyQuotationsScreen(viewModel)
        }
        composable(
            route = Screen.Quotation.route,
            arguments = listOf(navArgument("quoteId") { type = NavType.IntType })
        ) {
            val quoteId = it.arguments?.getInt("quoteId")!!
            val quoteToDelete = uiState.quotes.find { it.id == quoteId }
            QuotationContent(
                quoteId,
                onBackClick = { navController.navigateUp() },
                onDeleteClick = {
                    quoteToDelete?.let {
                        viewModel.deleteQuote(it)
                        navController.navigateUp()
                    }
                }
            )
        }
    }
}
