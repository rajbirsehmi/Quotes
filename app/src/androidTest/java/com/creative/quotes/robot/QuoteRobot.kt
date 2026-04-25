package com.creative.quotes.robot

import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick

class QuoteRobot(composeTestRule: ComposeContentTestRule) : BaseRobot(composeTestRule) {

    // --- Screen 1 Actions (Add/Subject List) ---
    fun enterQuoteText(text: String) = typeText("quote_input", text)
    fun enterAuthorName(text: String) = typeText("author_input", text)
    fun enterSubjectText(text: String) = typeText("subject_input", text)
    fun enterReferenceText(text: String) = typeText("reference_input", text)

    fun clickSaveButton() {
        clickOnTag("bottom_sheet_save_button")
    }

    fun selectSubject(subjectName: String) {
        composeTestRule.onNodeWithText(subjectName).performClick()
    }

    // --- Screen 3 Actions (Update Bottom Sheet) ---
    fun clickEditButton() {
        clickOnTag("top_bar_edit_button")
    }

    fun updateQuoteInBottomSheet(newText: String) {
        typeText("quote_input", newText)
        clickOnTag("bottom_sheet_edit_button")
    }

    // --- Verifications ---
    fun verifyQuoteIsDisplayed(text: String) {
        composeTestRule.onNodeWithText(text).assertIsDisplayed()
    }

    fun verifyAuthorVisibility(author: String) {
        composeTestRule.onNodeWithText(author).assertIsDisplayed()
    }

    fun verifyEmptyStateScreenMessageIsDisplaying() {
        composeTestRule.onNodeWithTag("text_empty_message_text").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_empty_message_subtext").assertIsDisplayed()
    }

    fun verifyTopAppBarTitleIsDisplaying() {
        composeTestRule.onNodeWithTag("top_bar_title").assertIsDisplayed()
    }

    fun verifySubjectTopAppBarShouldDisplaySubjectTest(text: String) {
        composeTestRule.onNodeWithTag("top_bar_title").assertTextEquals(text)
    }

    fun verifyTopAppBarAddButtonIsDisplaying() {
        composeTestRule.onNodeWithTag("top_bar_add_button").assertIsDisplayed()
    }

    fun verifyTopAppBarBackButtonIsDisplaying() {
        composeTestRule.onNodeWithTag("top_bar_back_button").assertIsDisplayed()
    }

    fun verifyTopAppBarShareButtonIsDisplaying() {
        composeTestRule.onNodeWithTag("top_bar_share_button").assertIsDisplayed()
    }

    fun verifyBottomSheetTitleIsDisplayed() {
        composeTestRule.onNodeWithTag("bottom_sheet_title").assertIsDisplayed()
    }

    fun verifyTopAppBarSettingButtonIsDisplaying() {
        composeTestRule.onNodeWithTag("top_bar_settings_button").assertIsDisplayed()
    }

    fun verifyTopAppBarEditButtonIsDisplaying() {
        composeTestRule.onNodeWithTag("top_bar_edit_button").assertIsDisplayed()
    }

    fun verifyTopAppBarDeleteButtonIsDisplaying() {
        composeTestRule.onNodeWithTag("top_bar_delete_button").assertIsDisplayed()
    }

    fun verifyTopAppBarRestoreButtonIsDisplaying() {
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithTag("top_bar_restore_button", useUnmergedTree = true).fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithTag("top_bar_restore_button", useUnmergedTree = true).assertIsDisplayed()
    }

    fun verifyTopAppBarBackupButtonIsDisplaying() {
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithTag("top_bar_backup_button", useUnmergedTree = true).fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithTag("top_bar_backup_button", useUnmergedTree = true).assertIsDisplayed()
    }

    fun verifySubjectIsDisplayed(subject: String) {
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithTag("subject_card").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithTag("subject_card").assertIsDisplayed()
        composeTestRule.onNodeWithText(subject).assertIsDisplayed()
    }

    fun verifyBottomSheetIsNotDisplaying() {
        composeTestRule.onNodeWithTag("bottom_sheet_title").assertDoesNotExist()
    }

    fun verifyQuoteDetailsAreDisplaying(
        quoteTestQuote: String,
        authorText: String,
        referenceText: String,
        subjectText: String
    ) {
        composeTestRule.onNodeWithTag("quote_text").assertTextEquals(quoteTestQuote)
        composeTestRule.onNodeWithTag("quote_author_text").assertTextEquals(authorText)
        composeTestRule.onNodeWithTag("quote_reference_text").assertTextEquals(referenceText)
        composeTestRule.onNodeWithTag("quote_subject_text").assertTextEquals(subjectText)
    }

    // DSL entry point
    companion object {
        fun withRobot(
            composeTestRule: ComposeContentTestRule,
            func: QuoteRobot.() -> Unit
        ) = QuoteRobot(composeTestRule).apply { func() }
    }
}