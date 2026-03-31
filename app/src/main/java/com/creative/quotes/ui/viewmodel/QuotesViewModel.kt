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

    private val _selectedSubject = MutableStateFlow("")

    fun setSubject(subject: String) {
        _selectedSubject.value = subject
    }

    private val subjectsFlow = quotesRepository.getAllSubjects()

    private val quotesBySubjectFlow = _selectedSubject.flatMapLatest { subject ->
        quotesRepository.getAllQuotesBySubject(subject)
    }

    val uiState: StateFlow<QuotesUiState> = combine(
        quotesBySubjectFlow,
        subjectsFlow
    ) { quotes, subjects ->
        QuotesUiState(
            quotes = quotes,
            subjects = subjects,
            quotesBySubject = quotes,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(100),
        initialValue = QuotesUiState(isLoading = true)
    )

    fun addQuote(quote: Quote) {
        viewModelScope.launch { quotesRepository.insertQuote(quote) }
    }

    fun deleteQuote(quote: Quote) {
        viewModelScope.launch { quotesRepository.deleteQuote(quote) }
    }

    fun updateQuote(quote: Quote) {
        viewModelScope.launch { quotesRepository.updateQuote(quote) }
    }

    fun getQuoteById(id: Int) = quotesRepository.getQuoteById(id)
}