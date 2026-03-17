package com.creative.quotes.ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.quotes.domain.model.Quote
import com.creative.quotes.domain.repo.QuotesRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BackupViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository
) : ViewModel() {
    fun createBackup(uri: Uri, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val quotes = quotesRepository.getAllQuotes().first()
                val json = Gson().toJson(quotes)
                context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                    outputStream.write(json.toByteArray())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun restoreBackup(uri: Uri, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                    val json = context.contentResolver.openInputStream(uri)?.use {
                        inputStream ->
                        inputStream.bufferedReader().use { it.readText() }
                    }
                    val type = object : TypeToken<List<Quote>>() {}.type
                    val quotes = Gson().fromJson<List<Quote>>(json, type)
                    quotesRepository.insertAllQuotes(quotes)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }