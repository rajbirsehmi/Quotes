package com.creative.quotes.ui.navigation

sealed class Screen(val route: String) {
    object EmptyQuotationsScreen: Screen("empty_quotations_screen")
    object AllQuotationsScreen: Screen("all_quotations_screen")
    object Quotation : Screen("quotation/{quoteId}") {
        fun createRoute(quoteId: Int) = "quotation/$quoteId"
    }

    object AllSubjectsScreen: Screen("all_subjects_screen")

    object AllAuthorsScreen: Screen("all_authors_screen")

    object QuotesBySubjectScreen: Screen("subject_quotes/{subject}") {
        fun createRoute(subject: String) = "subject_quotes/$subject"
    }
}