package com.creative.quotes.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.creative.quotes.ui.components.AddQuoteBottomSheet
import com.creative.quotes.ui.components.SubjectCard
import com.creative.quotes.ui.components.TopBarAllSubjects
import com.creative.quotes.ui.viewmodel.BackupViewModel
import com.creative.quotes.ui.viewmodel.QuotesViewModel

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
            TopBarAllSubjects(
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
            }
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