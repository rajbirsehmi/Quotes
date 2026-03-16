package com.creative.quotes.ui.navigation

import com.creative.quotes.domain.model.Quote

data class QuotesUiState(
    val quotes: List<Quote> = emptyList(),
    val isLoading: Boolean = true
)