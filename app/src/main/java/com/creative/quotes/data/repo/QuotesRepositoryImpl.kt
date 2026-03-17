package com.creative.quotes.data.repo

import com.creative.quotes.data.local.QuoteDao
import com.creative.quotes.domain.model.Quote
import com.creative.quotes.domain.repo.QuotesRepository
import com.creative.quotes.util.toDomain
import com.creative.quotes.util.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QuotesRepositoryImpl @Inject constructor(
    private val quotesDao: QuoteDao
): QuotesRepository {
    override suspend fun insertQuote(quote: Quote) {
        quotesDao.insertQuote(quote.toEntity())
    }

    override fun getQuoteById(id: Int): Flow<Quote> =
        quotesDao.getQuoteById(id).map {
            entity -> entity.toDomain()
        }

    override fun getAllQuotes(): Flow<List<Quote>> =
        quotesDao.getAllQuotes().map {
            quotes -> quotes.map {
                it.toDomain()
            }
        }

    override suspend fun deleteQuote(quote: Quote) {
        quotesDao.deleteQuote(quote.toEntity())
    }

    override fun getAllSubjects(): Flow<List<String>> =
        quotesDao.getAllSubjects().map {
            quotes -> quotes.map {
                it
            }
        }

    override fun getAllAuthors(): Flow<List<String>> =
        quotesDao.getAllAuthors().map { quotes ->
            quotes.map {
                it
            }
        }

    override suspend fun updateQuote(quote: Quote) {
        quotesDao.updateQuote(quote.toEntity())
    }

    override fun getAllQuotesBySubject(subject: String): Flow<List<Quote>> = quotesDao.getQuotesBySubject(subject)
}