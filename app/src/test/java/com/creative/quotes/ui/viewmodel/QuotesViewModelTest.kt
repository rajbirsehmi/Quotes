package com.creative.quotes.ui.viewmodel

import app.cash.turbine.test
import com.creative.quotes.domain.model.Quote
import com.creative.quotes.domain.repo.QuotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class QuotesViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var repository: QuotesRepository

    private lateinit var viewModel: QuotesViewModel

    private val testQuote = Quote(
        id = 1,
        quote = "Test Quote",
        author = "Test Author",
        reference = "Test Reference",
        subject = "Test Subject",
        timestamp = 123456789L
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        whenever(repository.getAllQuotes()).thenReturn(flowOf(listOf(testQuote)))
        whenever(repository.getAllSubjects()).thenReturn(flowOf(listOf("Test Subject")))
        whenever(repository.getAllQuotesBySubject("")).thenReturn(flowOf(listOf(testQuote)))

        viewModel = QuotesViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState initially emits loading state and then data`() = runTest {
        viewModel.uiState.test {
            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            val successState = awaitItem()
            assertFalse(successState.isLoading)
            assertEquals(listOf(testQuote), successState.quotes)
            assertEquals(listOf("Test Subject"), successState.subjects)
        }
    }

    @Test
    fun `updateQuoteText updates formState correctly`() = runTest {
        viewModel.updateQuoteText("New Quote")
        assertEquals("New Quote", viewModel.formState.value.quoteText)
        assertFalse(viewModel.formState.value.isQuoteError)

        viewModel.updateQuoteText("")
        assertTrue(viewModel.formState.value.isQuoteError)
    }

    @Test
    fun `addQuote calls repository insertQuote`() = runTest {
        viewModel.addQuote(testQuote)
        testDispatcher.scheduler.advanceUntilIdle()
        verify(repository).insertQuote(testQuote)
    }

    @Test
    fun `validateAndGetQuote returns quote when valid`() {
        viewModel.updateQuoteText("Valid Quote")
        viewModel.updateAuthor("Valid Author")
        viewModel.updateReference("Valid Reference")
        viewModel.updateSubject("Valid Subject")

        val result = viewModel.validateAndGetQuote()
        assertTrue(result != null)
        assertEquals("Valid Quote", result?.quote)
    }

    @Test
    fun `validateAndGetQuote returns null when invalid`() {
        viewModel.updateQuoteText("")
        val result = viewModel.validateAndGetQuote()
        assertTrue(result == null)
        assertTrue(viewModel.formState.value.isQuoteError)
    }
}
