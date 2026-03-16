package com.creative.quotes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.creative.quotes.domain.model.Quote
import com.creative.quotes.ui.components.AddQuoteBottomSheet
import com.creative.quotes.ui.components.DeleteConfirmationDialog
import com.creative.quotes.ui.components.EditQuoteBottomSheet
import com.creative.quotes.ui.components.TopAppBarQuotesContent
import com.creative.quotes.ui.viewmodel.QuotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuotationContent(
    quoteId: Int,
    onBackClick: () -> Unit,
    onDeleteClick: (Quote) -> Unit,
    viewModel: QuotesViewModel = hiltViewModel()
) {
    val quote by viewModel.getQuoteById(quoteId).collectAsStateWithLifecycle(initialValue = null)

    var showDeleteDialog by remember { mutableStateOf(false) }

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

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
                onDelete = {showDeleteDialog = true},
                onEdit = {
                        showBottomSheet = true
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            quote?.let {
                Text(text = it.quote, style = MaterialTheme.typography.headlineMedium)
                Text(text = "— ${it.author}", style = MaterialTheme.typography.bodyLarge)
            } ?: Text("Loading or Quote not found...")
        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            dragHandle = null,
        ) {
            EditQuoteBottomSheet(onQuoteEdited = {
                viewModel.updateQuote(it)
                showBottomSheet = false
            }, quote)
        }
    }
}