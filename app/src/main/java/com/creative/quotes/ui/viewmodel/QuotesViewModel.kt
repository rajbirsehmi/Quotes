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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
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
    private val allQuotesFlow = quotesRepository.getAllQuotes()

    private val quotesBySubjectFlow = _selectedSubject.flatMapLatest { subject ->
        if (subject.isEmpty()) allQuotesFlow else quotesRepository.getAllQuotesBySubject(subject)
    }

    val uiState: StateFlow<QuotesUiState> = combine(
        allQuotesFlow,
        subjectsFlow,
        quotesBySubjectFlow
    ) { allQuotes, subjects, quotesBySub ->
        QuotesUiState(
            quotes = allQuotes,
            subjects = subjects,
            quotesBySubject = quotesBySub,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = QuotesUiState(isLoading = true)
    )

    // Consolidated Form State
    private val _formState = MutableStateFlow(QuoteFormState())
    val formState = _formState.asStateFlow()

    fun updateQuoteText(text: String) {
        _formState.update { it.copy(quoteText = text, isQuoteError = text.isBlank()) }
    }

    fun updateAuthor(author: String) {
        _formState.update { it.copy(author = author, isAuthorError = author.isBlank()) }
    }

    fun updateReference(reference: String) {
        _formState.update { it.copy(reference = reference, isReferenceError = reference.isBlank()) }
    }

    fun updateSubject(subject: String) {
        _formState.update { it.copy(subject = subject, isSubjectError = subject.isBlank()) }
    }

    fun resetForm() {
        _formState.value = QuoteFormState()
    }

    fun loadQuote(quote: Quote) {
        _formState.value = QuoteFormState(
            quoteText = quote.quote,
            author = quote.author,
            reference = quote.reference,
            subject = quote.subject
        )
    }

    fun validateAndGetQuote(id: Int? = null): Quote? {
        val s = _formState.value
        val q = s.quoteText.trim()
        val a = s.author.trim()
        val r = s.reference.trim()
        val sub = s.subject.trim()

        val isQError = q.isBlank()
        val isAError = a.isBlank()
        val isRError = r.isBlank()
        val isSubError = sub.isBlank()

        _formState.update { 
            it.copy(
                isQuoteError = isQError,
                isAuthorError = isAError,
                isReferenceError = isRError,
                isSubjectError = isSubError
            )
        }

        return if (!isQError && !isAError && !isRError && !isSubError) {
            Quote(id, q, a, r, sub, System.currentTimeMillis())
        } else {
            null
        }
    }

    fun addQuote(quote: Quote) {
        viewModelScope.launch { 
            quotesRepository.insertQuote(quote)
            resetForm()
        }
    }

    fun deleteQuote(quote: Quote) {
        viewModelScope.launch { quotesRepository.deleteQuote(quote) }
    }

    fun updateQuote(quote: Quote) {
        viewModelScope.launch { 
            quotesRepository.updateQuote(quote)
            resetForm()
        }
    }

    fun getQuoteById(id: Int) = quotesRepository.getQuoteById(id)
}
