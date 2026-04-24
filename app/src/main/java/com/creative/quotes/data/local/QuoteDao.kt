package com.creative.quotes.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

    @Upsert
    suspend fun insertQuote(quote: QuoteEntity)

    @Upsert
    suspend fun insertQuotes(quotes: List<QuoteEntity>)

    @Query("SELECT * FROM quotes WHERE id = :id")
    fun getQuoteById(id: Int): Flow<QuoteEntity?>

    @Query("SELECT * FROM quotes")
    fun getAllQuotes(): Flow<List<QuoteEntity>>

    @Delete
    suspend fun deleteQuote(quote: QuoteEntity)

    @Query("SELECT DISTINCT subject FROM quotes ORDER BY subject ASC")
    fun getAllSubjects(): Flow<List<String>>

    @Query("SELECT * FROM quotes WHERE subject = :subject ORDER BY quote ASC")
    fun getQuotesBySubject(subject: String): Flow<List<QuoteEntity>>

    @Query("SELECT DISTINCT author FROM quotes ORDER BY author ASC")
    fun getAllAuthors(): Flow<List<String>>

    @Update
    suspend fun updateQuote(quote: QuoteEntity)
}