package com.creative.quotes.ui.viewmodel

data class QuoteFormState(
    val quoteText: String = "",
    val author: String = "",
    val reference: String = "",
    val subject: String = "",
    val isQuoteError: Boolean = false,
    val isAuthorError: Boolean = false,
    val isReferenceError: Boolean = false,
    val isSubjectError: Boolean = false
)
