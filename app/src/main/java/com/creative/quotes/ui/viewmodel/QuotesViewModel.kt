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
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = QuotesUiState(isLoading = true)
    )

    // Form State
    private val _quoteText = MutableStateFlow("")
    val quoteText = _quoteText.asStateFlow()

    private val _author = MutableStateFlow("")
    val author = _author.asStateFlow()

    private val _reference = MutableStateFlow("")
    val reference = _reference.asStateFlow()

    private val _subject = MutableStateFlow("")
    val subject = _subject.asStateFlow()

    private val _isQuoteError = MutableStateFlow(false)
    val isQuoteError = _isQuoteError.asStateFlow()

    private val _isAuthorError = MutableStateFlow(false)
    val isAuthorError = _isAuthorError.asStateFlow()

    private val _isReferenceError = MutableStateFlow(false)
    val isReferenceError = _isReferenceError.asStateFlow()

    private val _isSubjectError = MutableStateFlow(false)
    val isSubjectError = _isSubjectError.asStateFlow()

    fun updateQuoteText(text: String) {
        _quoteText.value = text
        if (text.isNotBlank()) _isQuoteError.value = false
    }

    fun updateAuthor(author: String) {
        _author.value = author
        if (author.isNotBlank()) _isAuthorError.value = false
    }

    fun updateReference(reference: String) {
        _reference.value = reference
        if (reference.isNotBlank()) _isReferenceError.value = false
    }

    fun updateSubject(subject: String) {
        _subject.value = subject
        if (subject.isNotBlank()) _isSubjectError.value = false
    }

    fun resetForm() {
        _quoteText.value = ""
        _author.value = ""
        _reference.value = ""
        _subject.value = ""
        _isQuoteError.value = false
        _isAuthorError.value = false
        _isReferenceError.value = false
        _isSubjectError.value = false
    }

    fun loadQuote(quote: Quote) {
        _quoteText.value = quote.quote
        _author.value = quote.author
        _reference.value = quote.reference
        _subject.value = quote.subject
    }

    fun validateAndGetQuote(id: Int? = null): Quote? {
        val q = _quoteText.value.trim()
        val a = _author.value.trim()
        val r = _reference.value.trim()
        val s = _subject.value.trim()

        _isQuoteError.value = q.isBlank()
        _isAuthorError.value = a.isBlank()
        _isReferenceError.value = r.isBlank()
        _isSubjectError.value = s.isBlank()

        return if (!_isQuoteError.value && !_isAuthorError.value && !_isReferenceError.value && !_isSubjectError.value) {
            Quote(id, q, a, r, s, System.currentTimeMillis())
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
