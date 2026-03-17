package com.creative.quotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.quotes.domain.model.Quote
import com.creative.quotes.domain.repo.QuotesRepository
import com.creative.quotes.ui.navigation.QuotesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository
) : ViewModel() {

    private val _selectedSubject = MutableStateFlow<String?>(null)

    fun setSubject(subject: String?) {
        _selectedSubject.value = subject
    }

    val uiState: StateFlow<QuotesUiState> = _selectedSubject
        .flatMapLatest { subject ->
            val quotesFlow = if (subject == null) {
                quotesRepository.getAllQuotes()
            } else {
                quotesRepository.getAllQuotesBySubject(subject)
            }
            combine(
                quotesFlow,
                quotesRepository.getAllSubjects()
            ) { quotes, subjects ->
                QuotesUiState(
                    quotes = quotes,
                    subjects = subjects,
                    quotesBySubject = if (subject != null) quotes else emptyList(),
                    isLoading = false
                )
            }
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

    fun getAllQuotesBySubject(subject: String) = quotesRepository.getAllQuotesBySubject(subject)


    fun updateQuote(quote: Quote) {
        viewModelScope.launch {
            quotesRepository.updateQuote(quote)
        }
    }

    fun getAllSubjects() = quotesRepository.getAllSubjects()
}
