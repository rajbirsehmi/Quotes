package com.creative.quotes.robot

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithText

class QuoteDetailRobot(composeTestRule: ComposeContentTestRule) : BaseRobot(composeTestRule) {

    fun verifyFullQuoteDetails(text: String, author: String, reference: String) {
        composeTestRule.onNodeWithText(text).assertIsDisplayed()
        composeTestRule.onNodeWithText(author).assertIsDisplayed()
        composeTestRule.onNodeWithText(reference).assertIsDisplayed()
    }

    fun openUpdateSheet() {
        clickOnTag("edit_quote_icon")
    }

    fun verifyUpdatedQuote(newText: String) {
        composeTestRule.onNodeWithText(newText).assertIsDisplayed()
    }
}