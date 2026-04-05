package com.creative.quotes

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.creative.quotes.robot.QuoteData
import com.creative.quotes.robot.QuoteRobot
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class QuoteUiAutomation {

    private fun robot(block: QuoteRobot.() -> Unit) = robot.apply(block)

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var robot: QuoteRobot

    @Before
    fun setup() {
        hiltRule.inject()
        robot = QuoteRobot(composeTestRule)
    }

    @Test
    fun testFullUserJourney() {
        val testQuote = QuoteData(
            quote = "Consistency is the key to success.",
            author = "Unknown",
            reference = "Motivational Book",
            subject = "Success"
        )

        val updatedQuote = testQuote.copy(
            quote = "Consistency is the true key to success.",
            author = "Wise Person"
        )

        robot {
            clickAddButton()
            fillQuoteForm(testQuote)
            clickSave()

            verifySubjectIsDisplayed(testQuote.subject)
            clickOnSubject(testQuote.subject)

            verifyQuoteIsDisplayed(testQuote.quote)

            clickOnQuote(testQuote.quote)
            verifyQuoteDetail(testQuote)

            clickEditButton()
            fillQuoteForm(updatedQuote)
            clickUpdate()
            verifyQuoteDetail(updatedQuote)

            clickDeleteButton()
            clickCancelOnDialog()
            verifyQuoteDetail(updatedQuote)

            clickDeleteButton()
            clickDeleteOnDialog()

            verifyEmptyState()
        }
    }
}