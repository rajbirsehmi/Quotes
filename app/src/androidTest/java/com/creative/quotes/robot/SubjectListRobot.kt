package com.creative.quotes.robot

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick

class SubjectListRobot(composeTestRule: ComposeContentTestRule) : BaseRobot(composeTestRule) {

    fun clickOnQuote(quoteSnippet: String) {
        composeTestRule.onNodeWithText(quoteSnippet).performClick()
    }

    fun verifyQuoteInList(quoteText: String) {
        composeTestRule.onNodeWithText(quoteText).assertIsDisplayed()
    }
}