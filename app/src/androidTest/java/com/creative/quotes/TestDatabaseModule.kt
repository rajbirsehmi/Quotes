package com.creative.quotes

import android.content.Context
import androidx.room.Room
import com.creative.quotes.data.local.QuoteDao
import com.creative.quotes.data.local.QuotesDatabase
import com.creative.quotes.di.DatabaseModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)

object TestDatabaseModule {
    @Provides
    @Singleton
    fun provideInMemoryDb(@ApplicationContext context: Context): QuotesDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            QuotesDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun provideQuoteDao(db: QuotesDatabase): QuoteDao = db.quotesDao()
}