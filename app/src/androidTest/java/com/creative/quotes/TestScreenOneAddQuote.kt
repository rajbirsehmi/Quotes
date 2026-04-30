package com.creative.quotes

import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.test.filters.FlakyTest
import com.creative.quotes.ui.navigation.QuotesNavGraph
import com.creative.quotes.robot.QuoteRobot
import com.creative.quotes.robot.testQuote
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class TestScreenOneAddQuote {
    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeTestRule.setContent {
            QuotesNavGraph()
        }
    }

    @Test
    fun clickOnAddButton_verifyBottomSheetIsDisplayed() {
        QuoteRobot.withRobot(composeTestRule) {
            verifyTopAppBarAddButtonIsDisplaying()
            clickOnTag("top_bar_add_button")
            verifyBottomSheetTitleIsDisplayed()
        }
    }

    @Test
    fun clickOnSaveQuoteButton_verifyQuoteIsAdded() {
        QuoteRobot.withRobot(composeTestRule) {
            clickOnTag("top_bar_add_button")
            enterQuoteText(testQuote.quote)
            enterAuthorName(testQuote.author)
            enterSubjectText(testQuote.subject)
            enterReferenceText(testQuote.reference)
            clickSaveButton()
            composeTestRule.waitForIdle()
            verifySubjectIsDisplayed(testQuote.subject)
        }
    }
}