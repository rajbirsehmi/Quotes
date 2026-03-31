package com.creative.quotes

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class QuotesComposeTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testAddQuote_Flow() {
        composeTestRule.onNodeWithText("Quotations").assertIsDisplayed()
        helper_AddQuoteData()
        composeTestRule.onNodeWithText("Compose Subject").assertIsDisplayed()
    }

    @Test
    fun testNavigation_ToDetails() {
        helper_AddQuoteData()
        composeTestRule.onNodeWithText("Compose Subject").performClick()
        composeTestRule.onNodeWithText("Compose Quote").assertIsDisplayed()
        composeTestRule.onNodeWithText("Compose Subject").assertIsDisplayed()
        composeTestRule.onNodeWithText("-- Compose Author, ").assertIsDisplayed()
        composeTestRule.onNodeWithText("Compose Reference").assertIsDisplayed()
    }

    @Test
    fun testNavigation_QuotationDetails() {
        helper_AddQuoteData()
        composeTestRule.onNodeWithText("Compose Subject").performClick()
        composeTestRule.onNodeWithText("Compose Quote").performClick()
        composeTestRule.onNodeWithText("Quotation Details").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Back to quotations").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Edit Quotation...").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Delete Quotation...").assertIsDisplayed()
        composeTestRule.onNodeWithText("Compose Quote").assertIsDisplayed()
    }

    @Test
    fun testNavigation_BackToSubject() {
        helper_AddQuoteData()
        composeTestRule.onNodeWithText("Compose Subject").performClick()
        composeTestRule.onNodeWithContentDescription("Back to Subjects").performClick()
        composeTestRule.onNodeWithText("Quotations").assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    private fun helper_AddQuoteData() {
        composeTestRule.onNodeWithContentDescription("Add Quotation...").performClick()
        composeTestRule.onNodeWithTag("quote_input").performTextInput("Compose Quote")
        composeTestRule.onNodeWithText("Author").performTextInput("Compose Author")
        composeTestRule.onNodeWithText("Reference").performTextInput("Compose Reference")
        composeTestRule.onNodeWithText("Subject").performTextInput("Compose Subject")
        composeTestRule.onNodeWithText("Save Quote").performClick()

//        composeTestRule.waitUntilDoesNotExist(hasText("Save Quote"), timeoutMillis = 500)

//        composeTestRule.waitForIdle()
//        composeTestRule.onNode(isRoot()).performClick()
    }

    private fun helper_UpdateQuoteData() {
        composeTestRule.onNodeWithContentDescription("Edit Quotation...").performClick()
        composeTestRule.onNodeWithTag("quote_input").performTextInput("Updated Quote")
        composeTestRule.onNodeWithTag("author_input").performTextInput("Updated Author")
        composeTestRule.onNodeWithTag("reference_input").performTextInput("Updated Reference")
        composeTestRule.onNodeWithTag("subject_input").performTextInput("Compose Subject")
        composeTestRule.onNodeWithText("Update Quote").performClick()
    }
}