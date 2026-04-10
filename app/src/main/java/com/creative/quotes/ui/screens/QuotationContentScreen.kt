package com.creative.quotes.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.creative.quotes.domain.model.Quote
import com.creative.quotes.ui.components.DeleteConfirmationDialog
import com.creative.quotes.ui.components.EditQuoteBottomSheet
import com.creative.quotes.ui.components.QuotationDetails
import com.creative.quotes.ui.components.TopAppBarQuotesContent
import com.creative.quotes.ui.utils.ScreenshotUtils
import com.creative.quotes.ui.viewmodel.QuotesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuotationContent(
    quoteId: Int,
    onBackClick: () -> Unit,
    onDeleteClick: (Quote) -> Unit,
    viewModel: QuotesViewModel = hiltViewModel()
) {
    val quote by viewModel.getQuoteById(quoteId).collectAsStateWithLifecycle(initialValue = null)
    val context = LocalContext.current
    val view = LocalView.current

    var showDeleteDialog by remember { mutableStateOf(false) }

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()


    // Logic for the actual Alert Dialog
    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            onConfirm = {
                quote?.let { onDeleteClick(it) }
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBarQuotesContent(
                quote = quote,
                onBack = onBackClick,
                onDelete = { showDeleteDialog = true },
                onEdit = {
                    showBottomSheet = true
                },
                onShare = { quoteToShare ->
                    // Capture the view and share as image
                    ScreenshotUtils.captureAndShare(context, view, "quote_${quoteToShare.id}.png")
                }
            )
        }
    ) { innerPadding ->
        QuotationDetails(quote = quote, modifier = Modifier.padding(innerPadding))
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { 
                showBottomSheet = false 
                viewModel.resetForm()
            },
            sheetState = sheetState,
            dragHandle = null,
        ) {
            EditQuoteBottomSheet(
                viewModel = viewModel,
                onQuoteEdited = {
                    focusManager.clearFocus()
                    viewModel.updateQuote(it)
                    scope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        showBottomSheet = false
                    }
                }, 
                quote = quote
            )
        }
    }
}
