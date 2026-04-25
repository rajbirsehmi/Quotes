package com.creative.quotes

import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import com.creative.quotes.ui.navigation.QuotesNavGraph
import com.creative.quotes.robot.QuoteRobot
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class TestScreenOneEmpty {

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
    fun ifNoSubjectIsListed_verifyEmptyStateScreenMessage() {
        QuoteRobot.withRobot(composeTestRule) {
            verifyEmptyStateScreenMessageIsDisplaying()
        }
    }

    @Test
    fun topAppBar_verifyIfActionsAreDisplaying() {
        QuoteRobot.withRobot(composeTestRule) {
            verifyTopAppBarTitleIsDisplaying()
            verifyTopAppBarAddButtonIsDisplaying()
            verifyTopAppBarSettingButtonIsDisplaying()
        }
    }

    @Test
    fun clickOnSettingIcon_verifyEditAndDeleteOptionsShouldShow() {
        QuoteRobot.withRobot(composeTestRule) {
            verifyTopAppBarSettingButtonIsDisplaying()
            clickOnTag("top_bar_settings_button")
            composeTestRule.waitForIdle()
            verifyTopAppBarRestoreButtonIsDisplaying()
            verifyTopAppBarBackupButtonIsDisplaying()
        }
    }

    @Test
    fun testClickOnAddButton_verifyBottomSheetIsDisplayed() {
        QuoteRobot.withRobot(composeTestRule) {
            verifyTopAppBarAddButtonIsDisplaying()
            clickOnTag("top_bar_add_button")
            verifyBottomSheetTitleIsDisplayed()
        }
    }
}