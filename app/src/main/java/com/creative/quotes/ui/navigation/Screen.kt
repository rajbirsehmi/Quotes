package com.creative.quotes.ui.navigation

sealed class Screen(val route: String) {
    object EmptyQuotationsScreen: Screen("empty_quotations_screen")
    object AllQuotationsScreen: Screen("all_quotations_screen")
    object Quotation : Screen("quotation/{quoteId}") {
        fun createRoute(quoteId: Int) = "quotation/$quoteId"
    }
}