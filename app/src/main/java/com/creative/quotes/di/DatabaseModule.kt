package com.creative.quotes.di

import android.content.Context
import androidx.room.Room
import com.creative.quotes.data.local.QuoteDao
import com.creative.quotes.data.local.QuotesDatabase
import com.creative.quotes.util.QUOTES_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): QuotesDatabase {
        return Room.databaseBuilder(
            context,
            QuotesDatabase::class.java,
            QUOTES_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideQuoteDao(db: QuotesDatabase): QuoteDao {
        return db.quotesDao()
    }
}