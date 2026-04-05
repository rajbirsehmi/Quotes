package com.creative.quotes.robot

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement

class QuoteRobot(private val composeTestRule: ComposeContentTestRule) {
    fun clickAddButton() {
        composeTestRule.onNodeWithContentDescription("Add Quotation...").performClick()
    }

    fun fillQuoteForm(data: QuoteData) {
        composeTestRule.onNodeWithTag("quote_input").performTextReplacement(data.quote)
        composeTestRule.onNodeWithTag("author_input").performTextReplacement(data.author)
        composeTestRule.onNodeWithTag("reference_input").performTextReplacement(data.reference)
        composeTestRule.onNodeWithTag("subject_input").performTextReplacement(data.subject)
    }

    fun clickSave() {
        composeTestRule.onNodeWithText("Save Quote").performClick()
    }

    fun clickUpdate() {
        composeTestRule.onNodeWithText("Update Quote").performClick()
    }

    fun verifySubjectIsDisplayed(subject: String) {
        composeTestRule.onNodeWithText(subject).assertIsDisplayed()
    }

    fun clickOnSubject(subject: String) {
        composeTestRule.onNodeWithText(subject).performClick()
    }

    fun verifyQuoteIsDisplayed(quote: String) {
        composeTestRule.onNodeWithText(quote).assertIsDisplayed()
    }

    fun clickOnQuote(quote: String) {
        composeTestRule.onNodeWithText(quote).performClick()
    }

    fun verifyQuoteDetail(data: QuoteData) {
        composeTestRule.onNodeWithText(data.quote).assertIsDisplayed()
        composeTestRule.onNodeWithText("— ${data.author}").assertIsDisplayed()
    }

    fun clickEditButton() {
        composeTestRule.onNodeWithContentDescription("Edit Quotation...").performClick()
    }

    fun clickDeleteButton() {
        composeTestRule.onNodeWithContentDescription("Delete Quotation...").performClick()
    }

    fun clickDeleteOnDialog() {
        composeTestRule.onNodeWithText("Delete").performClick()
    }

    fun clickCancelOnDialog() {
        composeTestRule.onNodeWithText("Cancel").performClick()
    }

    fun goBack() {
        composeTestRule.onAllNodesWithContentDescription("Back to quotations").onFirst().performClick()
        composeTestRule.onAllNodesWithContentDescription("Back to Subjects").onFirst().performClick()
    }

    fun verifyEmptyState() {
        composeTestRule.onNodeWithText("No Quotations Yet.").assertIsDisplayed()
    }
}