package com.creative.quotes.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: QuoteEntity)

    @Query("SELECT * FROM quotes WHERE id = :id")
    fun getQuoteById(id: Int): Flow<QuoteEntity>

    @Query("SELECT * FROM quotes")
    fun getAllQuotes(): Flow<List<QuoteEntity>>

    @Delete
    suspend fun deleteQuote(quote: QuoteEntity)

    @Query("SELECT DISTINCT subject FROM quotes ORDER BY subject ASC")
    fun getAllSubjects(): Flow<List<String>>

    @Query("SELECT DISTINCT author FROM quotes ORDER BY author ASC")
    fun getAllAuthors(): Flow<List<String>>

    @Update
    suspend fun updateQuote(quote: QuoteEntity)

}