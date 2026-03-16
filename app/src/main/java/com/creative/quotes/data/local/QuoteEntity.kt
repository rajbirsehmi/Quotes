package com.creative.quotes.data.local

import android.provider.SyncStateContract
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.creative.quotes.util.QUOTES_ENTITY_NAME

@Entity(tableName = QUOTES_ENTITY_NAME)
data class QuoteEntity(

    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val quote: String,
    val author: String,
    val reference: String,
    val subject: String,
    val timestamp: Long
)
