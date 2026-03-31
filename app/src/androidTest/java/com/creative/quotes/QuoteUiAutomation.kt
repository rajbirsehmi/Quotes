package com.creative.quotes

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class QuoteUiAutomation {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private var packageName: String = "com.creative.quotes"
    private lateinit var device: UiDevice
    val testTestQuote = "Test Quote"

    val testTestAuthor = "Test Author"
    val textTestReference = "Test Reference"
    val textTestSubject = "Test Subject"
    val textNoQuotesFound = "No Quotations Yet."

    val textNewQuote = "This is a new Quote"
    val textNewAuthor = "New Author"
    val textNewReference = "Some New Reference"
    val textNewSubject = "New Subject"

    val textAddQuotation = "Add Quotation..."
    val textEditQuotation = "Edit Quotation..."
    val textDeleteQuotation = "Delete Quotation..."

    val textUpdateQuotation = "Update Quote"

    @Before
    fun setup() {
        hiltRule.inject()
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
    fun testFullUserJourney() {
        test01_AddQuote()
        test02_OpenTestQuote()
        test03_OpenFullQuoteDetail()
        test04_EditQuote()
        test05_DeleteQuote()
        test06_NoQuotesFound()
    }

//    @Test
    fun test01_AddQuote() {
        val btnAdd = device.wait(Until.findObject(By.desc(textAddQuotation)), 10000)
        btnAdd?.click() ?: throw AssertionError("Add button ('Add Quotation...') not found")

        device.wait(
            Until.findObject(By.clazz("android.widget.EditText").hasChild(By.text("Quote"))),
            10000
        )?.apply {
            click()
            text = testTestQuote
        } ?: throw AssertionError("Quote input field not found")

        device.findObject(By.clazz("android.widget.EditText").hasChild(By.text("Author")))?.apply {
            click()
            text = testTestAuthor
        } ?: throw AssertionError("Author input field not found")

        device.findObject(By.clazz("android.widget.EditText").hasChild(By.text("Reference")))
            ?.apply {
                click()
                text = textTestReference
            } ?: throw AssertionError("Reference input field not found")

        device.findObject(By.clazz("android.widget.EditText").hasChild(By.text("Subject")))?.apply {
            click()
            text = textTestSubject
        } ?: throw AssertionError("Subject input field not found")

        val btnSave = device.wait(Until.findObject(By.text("Save Quote")), 5000)
        btnSave?.click() ?: throw AssertionError("Save button not found")

        // Verify result
        val isQuoteAdded = device.wait(Until.hasObject(By.text(testTestQuote)), 5000)
        assert(isQuoteAdded != null)
    }

//    @Test
    fun test02_OpenTestQuote() {
        device.wait(Until.findObject(By.text(textTestSubject)), 5000)?.click()

        val isQuoteTextDisplayed = device.wait(Until.findObject(By.text(testTestQuote)), 5000)
        assert(isQuoteTextDisplayed != null) {
            "Quote not displayed"
        }

        // Updated selector to match the actual text format in your UI
        val isQuoteAuthorDisplayed =
            device.wait(Until.findObject(By.textContains(testTestAuthor)), 5000)
        assert(isQuoteAuthorDisplayed != null) {
            "Author not displayed"
        }

        val isQuoteReferenceDisplayed =
            device.wait(Until.findObject(By.textContains(textTestReference)), 5000)
        assert(isQuoteReferenceDisplayed != null) {
            "Reference not displayed"
        }
    }

//    @Test
    fun test03_OpenFullQuoteDetail() {
        device.wait(Until.findObject(By.text(textTestSubject)), 5000)?.click()
        device.wait(Until.findObject(By.text(testTestQuote)), 5000)?.click()

        val isQuoteTextDisplaying = device.wait(Until.findObject(By.text(testTestQuote)), 5000)
        assert(isQuoteTextDisplaying != null) {
            "Quote not displayed"
        }

        val isQuoteAuthorDisplaying =
            device.wait(Until.findObject(By.textContains(testTestAuthor)), 5000)
        assert(isQuoteAuthorDisplaying != null) {
            "Author not displayed"
        }
    }

//    @Test
    fun test04_EditQuote() {
        device.wait(Until.findObject(By.text(textTestSubject)), 5000)?.click()
        device.wait(Until.findObject(By.text(testTestQuote)), 5000)?.click()

        val btnEdit = device.wait(Until.findObject(By.desc(textEditQuotation)), 10000)
        btnEdit?.click() ?: throw AssertionError("Edit button ('Edit Quotation...') not found")

        // Use the constant labels "Quote", "Author", etc. as they are reliable anchors
        device.wait(
            Until.findObject(By.clazz("android.widget.EditText").hasChild(By.text("Quote"))),
            10000
        )?.apply {
            click()
            text = textNewQuote
        } ?: throw AssertionError("Quote input field not found")


        device.findObject(By.clazz("android.widget.EditText").hasChild(By.text("Author")))?.apply {
            click()
            text = textNewAuthor
        } ?: throw AssertionError("Author input field not found")

        device.findObject(By.clazz("android.widget.EditText").hasChild(By.text("Subject")))?.apply {
            click()
            text = textNewSubject
        } ?: throw AssertionError("Subject input field not found")

        device.findObject(By.clazz("android.widget.EditText").hasChild(By.text("Reference")))
            ?.apply {
                click()
                text = textNewReference
            } ?: throw AssertionError("Reference input field not found")

        // Use By.text for the update button since it contains the text "Update Quote"
        val btnUpdate = device.wait(Until.findObject(By.text(textUpdateQuotation)), 10000)
        btnUpdate?.click() ?: throw AssertionError("Update button ('Update Quote') not found")

        assert(
            device.wait(Until.findObject(By.text(textNewQuote)), 5000) != null
        ) { "Quote not updated" }

        assert(
            device.wait(Until.findObject(By.textContains(textNewAuthor)), 5000) != null
        ) { "Author not updated" }

        device.pressBack()
        device.pressBack()

        assert(
            device.wait(Until.findObject(By.text(textNewSubject)), 5000) != null
        ) { "Subject not Found" }
    }

//    @Test
    fun test05_DeleteQuote() {
        device.wait(Until.findObject(By.text(textNewSubject)), 5000).click()
        device.wait(Until.findObject(By.text(textNewQuote)), 500).click()

        val btnDelete = device.wait(Until.findObject(By.desc(textDeleteQuotation)), 10000)
        btnDelete.click()

        device.wait(Until.findObject(By.text("Cancel")), 5000).click()
        assert(device.wait(Until.findObject(By.text(textNewQuote)), 500) != null) {
            "Quote not deleted"
        }

        btnDelete.click()
        device.wait(Until.findObject(By.text("Delete")), 5000).click()
        assert(device.wait(Until.findObject(By.text(textNewQuote)), 500) == null) {
            "Quote not deleted"
        }
    }

//    @Test
    fun test06_NoQuotesFound() {
        assert(device.wait(Until.findObject(By.text(textNoQuotesFound)), 5000) != null)
    }
}
