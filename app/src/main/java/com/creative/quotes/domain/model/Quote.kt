package com.creative.quotes.domain.model

data class Quote(
    val id: Int?,
    val quote: String,
    val author: String,
    val reference: String,
    val subject: String,
    val timestamp: Long
)
