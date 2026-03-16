package com.creative.quotes.util

import com.creative.quotes.data.local.QuoteEntity
import com.creative.quotes.domain.model.Quote

fun QuoteEntity.toDomain(): Quote {
    return Quote(
        id = id,
        quote = quote,
        author = author,
        reference = reference,
        subject = subject,
        timestamp = timestamp
    )
}

fun Quote.toEntity(): QuoteEntity {
    return QuoteEntity(
        id = id,
        quote = quote,
        author = author,
        reference = reference,
        subject = subject,
        timestamp = timestamp
    )
}