package com.creative.quotes.robot

import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput

abstract class BaseRobot(val composeTestRule: ComposeContentTestRule) {
    fun clickOn(text: String) {
        composeTestRule.onNodeWithText(text).performClick()
    }

    fun clickOnTag(tag: String) {
        composeTestRule.onNodeWithTag(tag).performClick()
    }

    fun clickOnTagWithText(tag: String, text: String) {
        composeTestRule.onNode(
            hasTestTag(tag) and hasText(text)
        ).performClick()
    }

    fun typeText(tag: String, text: String) {
        composeTestRule.onNodeWithTag(tag).performTextInput(text)
    }
}