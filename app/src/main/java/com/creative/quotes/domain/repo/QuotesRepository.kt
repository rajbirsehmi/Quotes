package com.creative.quotes.domain.repo

import com.creative.quotes.domain.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuotesRepository {

    suspend fun insertQuote(quote: Quote)

    fun getQuoteById(id: Int): Flow<Quote>

    fun getAllQuotes(): Flow<List<Quote>>

    suspend fun deleteQuote(quote: Quote)

    fun getAllSubjects(): Flow<List<String>>

    fun getAllAuthors(): Flow<List<String>>

    suspend fun updateQuote(quote: Quote)
}

