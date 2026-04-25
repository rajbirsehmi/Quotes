package com.creative.quotes.robot

data class TestQuote(
    val id: Int,
    val quote: String,
    val author: String,
    val reference: String,
    val subject: String
)

val testQuote: TestQuote = TestQuote(
    id = 1,
    quote = "This is a quote",
    author = "Author",
    reference = "Reference",
    subject = "Subject"
)

var updatedQuote: TestQuote = testQuote.copy(
    quote = "This is an updated quote",
    author = "Updated Author",
    reference = "Updated Reference"
)
