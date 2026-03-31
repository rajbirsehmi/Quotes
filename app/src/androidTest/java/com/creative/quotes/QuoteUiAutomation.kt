package com.creative.quotes

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class QuoteUiAutomation {
    private var packageName: String = "com.creative.quotes"
    private lateinit var device: UiDevice

    @Before
    fun setup() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)?.apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000)
    }

    @Test
    fun test01_AddQuote() {
        val btnAdd = device.wait(Until.findObject(By.desc("Add Quotation...")), 10000)
        btnAdd?.click() ?: throw AssertionError("Add button ('Add Quotation...') not found")

        device.wait(
            Until.findObject(By.clazz("android.widget.EditText").hasChild(By.text("Quote"))),
            10000
        )?.apply {
            click()
            text = "Test Quote"
        } ?: throw AssertionError("Quote input field not found")

        device.findObject(By.clazz("android.widget.EditText").hasChild(By.text("Author")))?.apply {
            click()
            text = "Test Author"
        } ?: throw AssertionError("Author input field not found")

        device.findObject(By.clazz("android.widget.EditText").hasChild(By.text("Reference")))?.apply {
            click()
            text = "Test Reference"
        } ?: throw AssertionError("Reference input field not found")

        device.findObject(By.clazz("android.widget.EditText").hasChild(By.text("Subject")))?.apply {
            click()
            text = "Test Subject"
        } ?: throw AssertionError("Subject input field not found")

        val btnSave = device.wait(Until.findObject(By.text("Save Quote")), 5000)
        btnSave?.click() ?: throw AssertionError("Save button not found")

        // Verify result
        val isQuoteAdded = device.wait(Until.hasObject(By.text("Test Quote")), 5000)
        assert(isQuoteAdded != null)
    }

    @Test
    fun test02_OpenTestQuote() {
        device.wait(Until.findObject(By.text("Test Subject")), 5000)?.click()

        val isQuoteTextDisplayed = device.wait(Until.findObject(By.text("Test Quote")), 5000)
        assert(isQuoteTextDisplayed != null) {
            "Quote not displayed"
        }

        // Updated selector to match the actual text format in your UI
        val isQuoteAuthorDisplayed = device.wait(Until.findObject(By.textContains("Test Author")), 5000)
        assert(isQuoteAuthorDisplayed != null) {
            "Author not displayed"
        }

        val isQuoteReferenceDisplayed = device.wait(Until.findObject(By.textContains("Test Reference")), 5000)
        assert(isQuoteReferenceDisplayed != null) {
            "Reference not displayed"
        }
    }

    @Test
    fun test03_OpenFullQuoteDetail() {
        device.wait(Until.findObject(By.text("Test Subject")), 5000)?.click()
        device.wait(Until.findObject(By.text("Test Quote")), 5000)?.click()

        val isQuoteTextDisplaying = device.wait(Until.findObject(By.text("Test Quote")), 5000)
        assert(isQuoteTextDisplaying != null) {
            "Quote not displayed"
        }

        val isQuoteAuthorDisplaying = device.wait(Until.findObject(By.textContains("Test Author")), 5000)
        assert(isQuoteAuthorDisplaying != null) {
            "Author not displayed"
        }
    }
}
