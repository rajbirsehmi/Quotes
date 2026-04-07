package com.creative.quotes.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.creative.quotes.ui.components.AddQuoteBottomSheet
import com.creative.quotes.ui.components.SubjectCard
import com.creative.quotes.ui.components.TopAppBarAllQuotes
import com.creative.quotes.ui.viewmodel.BackupViewModel
import com.creative.quotes.ui.viewmodel.QuotesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllSubjectsScreen(
    onSubjectClick: (String) -> Unit,
    viewModel: QuotesViewModel = hiltViewModel(),
    backupViewModel: BackupViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()

    val createBackupLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json")
    ) { uri ->
        uri?.let { backupViewModel.createBackup(it, context) }
    }

    // Launcher for Opening a file (Restore)
    val restoreBackupLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let { backupViewModel.restoreBackup(it, context) }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBarAllQuotes(
                onAddClick = {
                    showBottomSheet = true
                },
                onRestoreBackup = {
                    restoreBackupLauncher.launch(arrayOf("application/json"))
                },
                onCreateBackup = {
                    createBackupLauncher.launch("quotes_backup.json")
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(state.subjects) { subject ->
                SubjectCard(
                    subject,
                    onSubjectClick = { onSubjectClick(subject) }
                )
                HorizontalDivider(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    thickness = 0.5.dp
                )
            }
        }
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
            AddQuoteBottomSheet(
                viewModel = viewModel,
                onQuoteAdded = {
                    focusManager.clearFocus()
                    viewModel.addQuote(it)

                    scope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        showBottomSheet = false
                    }
                }
            )
        }
    }
}
