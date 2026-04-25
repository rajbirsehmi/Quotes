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
class TestScreenTwo {
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
            repository.insertQuote(
                Quote(
                    id = null,
                    quote = "This is another quote",
                    author = "Author 2",
                    reference = "Reference 2",
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
    fun clickOnSubject_verifyQuotesAreDisplayed() {
        QuoteRobot.withRobot(composeTestRule) {
            selectSubject("Subject")
            verifyQuoteIsDisplayed("This is a quote")
            verifyQuoteIsDisplayed("This is another quote")
        }
    }

    @Test
    fun topAppBar_verifySubjectTextIsDisplaying() {
        QuoteRobot.withRobot(composeTestRule) {
            clickOnTag("subject_card")
            verifyTopAppBarTitleIsDisplaying()
            verifySubjectTopAppBarShouldDisplaySubjectTest("Subject")
        }
    }

    @Test
    fun clickOnBackButton_verifySubjectsScreenIsDisplayed() {
        QuoteRobot.withRobot(composeTestRule) {
            clickOnTag("subject_card")
            verifyTopAppBarTitleIsDisplaying()
            verifyTopAppBarBackButtonIsDisplaying()
            clickOnTag("top_bar_back_button")
            composeTestRule.waitForIdle()
            verifySubjectIsDisplayed("Subject")
        }
    }
}