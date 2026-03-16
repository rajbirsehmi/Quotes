package com.creative.quotes.di

import com.creative.quotes.data.repo.QuotesRepositoryImpl
import com.creative.quotes.domain.repo.QuotesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindQuotesRepository(
        quotesRepositoryImpl: QuotesRepositoryImpl
    ): QuotesRepository

}