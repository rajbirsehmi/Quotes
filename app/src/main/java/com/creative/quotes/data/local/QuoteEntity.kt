package com.creative.quotes.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.creative.quotes.util.QUOTES_ENTITY_NAME

@Entity(
    tableName = QUOTES_ENTITY_NAME,
    indices = [
        Index(value = ["subject"]),
        Index(value = ["author"])
    ]
)
data class QuoteEntity(

    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val quote: String,
    val author: String,
    val reference: String,
    val subject: String,
    val timestamp: Long
)
