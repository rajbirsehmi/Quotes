package com.creative.quotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.quotes.domain.model.Quote
import com.creative.quotes.domain.repo.QuotesRepository
import com.creative.quotes.ui.navigation.QuotesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository
) : ViewModel() {
    val uiState: StateFlow<QuotesUiState> = quotesRepository.getAllQuotes().map {
        quotes -> QuotesUiState(quotes = quotes, isLoading = false)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = QuotesUiState(isLoading = true)
    )

    fun addQuote(quote: Quote) {
        viewModelScope.launch {
            quotesRepository.insertQuote(quote)
        }
    }

    fun deleteQuote(quote: Quote) {
        viewModelScope.launch {
            quotesRepository.deleteQuote(quote)
        }
    }

    fun getQuoteById(id: Int) = quotesRepository.getQuoteById(id)

    fun updateQuote(quote: Quote) {
        viewModelScope.launch {
            quotesRepository.updateQuote(quote)
        }
    }
}