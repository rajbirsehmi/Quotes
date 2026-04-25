package com.creative.quotes

import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import com.creative.quotes.domain.model.Quote
import com.creative.quotes.domain.repo.QuotesRepository
import com.creative.quotes.robot.QuoteRobot
import com.creative.quotes.ui.navigation.QuotesNavGraph
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class TestScreenThree {
    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var repository: QuotesRepository

    @Before
    fun setUp() {
        hiltRule.inject()

        runBlocking {
            repository.insertQuote(
                Quote(
                    id = null,
                    quote = "This is a quote",
                    author = "Author 1",
                    reference = "Reference 1",
                    subject = "Subject",
                    timestamp = System.currentTimeMillis()
                )
            )
        }

        composeTestRule.setContent {
            QuotesNavGraph()
        }
    }

    @Test
    fun clickOnQuote_verifyFullQuoteDetainShouldDisplay() {
        QuoteRobot.withRobot(composeTestRule) {
            selectSubject("Subject")
            clickOnTag("quote_card")
            verifyQuoteDetailsAreDisplaying(
                "This is a quote",
                "Author 1",
                "Reference 1",
                "Subject"
            )
        }
    }

    @Test
    fun clickOnQuote_verifyTopAppBarIsDisplaying() {
        QuoteRobot.withRobot(composeTestRule) {
            selectSubject("Subject")
            clickOnTag("quote_card")
            verifyTopAppBarTitleIsDisplaying()
            verifyTopAppBarBackButtonIsDisplaying()
            verifyTopAppBarShareButtonIsDisplaying()
        }
    }

    @Test
    fun clickOnThreeDots_verifyEditAndDeleteShowsUp() {
        QuoteRobot.withRobot(composeTestRule) {
            selectSubject("Subject")
            clickOnTag("quote_card")
            clickOnTag("top_bar_more_button")
            verifyTopAppBarEditButtonIsDisplaying()
            verifyTopAppBarDeleteButtonIsDisplaying()
        }
    }
}