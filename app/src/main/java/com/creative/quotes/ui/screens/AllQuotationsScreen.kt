package com.creative.quotes.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.creative.quotes.ui.components.AddQuoteBottomSheet
import com.creative.quotes.ui.components.QuotationCard
import com.creative.quotes.ui.components.TopAppBarAllQuotes
import com.creative.quotes.ui.viewmodel.QuotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllQuotationsScreen(
    onQuoteClick: (Int) -> Unit,
    viewModel: QuotesViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBarAllQuotes(onAddClick = {
                showBottomSheet = true
            })
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(state.quotes) { quote ->
                QuotationCard(
                    quote = quote,
                    onQuoteClick = { quote.id?.let { id -> onQuoteClick(id) } }
                )
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
                dragHandle = null,
            ) {
                AddQuoteBottomSheet(onQuoteAdded = {
                    viewModel.addQuote(it)
                    showBottomSheet = false
                })
            }
        }
    }
}
