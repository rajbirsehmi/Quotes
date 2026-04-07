package com.creative.quotes.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.creative.quotes.domain.model.Quote
import com.creative.quotes.ui.viewmodel.QuotesViewModel

@Composable
fun QuotationCard(
    quote: Quote,
    onQuoteClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onQuoteClick(quote.id ?: 0) }
            .testTag("quote_card")
    ) {
        Text(
            text = quote.quote,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(16.dp, 12.dp, 16.dp, 12.dp)
                .fillMaxWidth()
                .testTag("quote_text")
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 16.dp, 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.testTag("quote_author"),
                text = "-- ${quote.author}, ",
                style = MaterialTheme.typography.bodyMedium,
                minLines = 1
            )
            Text(
                modifier = Modifier.testTag("quote_reference"),
                text = quote.reference,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                minLines = 1
            )
        }
    }
}

@Composable
@Preview(
    name = "2 - Quotation Card",
    showBackground = true
)
fun QuotationCardPreview() {
    val quotes: List<Quote> = listOf(
        Quote(
            id = 1,
            quote = "This is a quote",
            author = "AuthorAuthorAuthor",
            reference = "ReferenceReference",
            subject = "Subject",
            timestamp = System.currentTimeMillis()
        ),
        Quote(
            id = 1,
            quote = "This is a quote",
            author = "Author",
            reference = "ReferenceReference",
            subject = "Subject",
            timestamp = System.currentTimeMillis()
        ),
        Quote(
            id = 1,
            quote = "This is a quote",
            author = "Author",
            reference = "Reference",
            subject = "Subject",
            timestamp = System.currentTimeMillis()
        ),
        Quote(
            id = 1,
            quote = "This is a quote",
            author = "Author",
            reference = "Reference",
            subject = "Subject",
            timestamp = System.currentTimeMillis()
        )
    )
    LazyColumn(
        modifier = Modifier
            .padding(16.dp, 12.dp, 16.dp, 12.dp)
            .navigationBarsPadding()
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        items(quotes) { quote ->
            QuotationCard(
                quote = quote,
                onQuoteClick = { }
            )
        }
    }
}

@Composable
fun SubjectCard(
    subject: String,
    onSubjectClick: (subject: String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
//            .padding(4.dp)
            .clickable { onSubjectClick(subject) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = subject,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(16.dp, 12.dp, 16.dp, 12.dp)
        )
        Spacer(
            modifier = Modifier.weight(1.0f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Open Subject",
            modifier = Modifier
                .padding(16.dp, 12.dp, 16.dp, 12.dp)
        )
    }
}

@Composable
@Preview(
    name = "1 - Subject Card",
    showBackground = true
)
fun SubjectCardPreview() {
    val subjects: List<String> = listOf(
        "Subject1",
        "Subject2",
        "Subject3",
        "Subject4"
    )
    LazyColumn(
        modifier = Modifier
            .padding(8.dp)
            .navigationBarsPadding()
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        items(subjects) { subject ->
            SubjectCard(subject, onSubjectClick = {})
        }
    }
}

@Composable
fun QuotationDetails(
    quote: Quote?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        quote?.let {
            Text(
                text = it.quote,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("quote_text"),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier.testTag("quote_author"),
                    text = "— ${it.author}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
@Preview(
    name = "3 - Quotation Details",
    showBackground = true
)
fun QuotationDetailsPreview() {
    val quote = Quote(
        id = 1,
        quote = "This is a quote",
        author = "Author",
        reference = "Reference",
        subject = "Subject",
        timestamp = System.currentTimeMillis()
    )
    QuotationDetails(
        quote = quote,
        modifier = Modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarAllQuotes(
    onAddClick: () -> Unit,
    onRestoreBackup: () -> Unit,
    onCreateBackup: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    CenterAlignedTopAppBar(
        title = {
            Text("Quotations")
        },
        actions = {
            IconButton(onClick = onAddClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Quotation..."
                )
            }
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings"
                    )
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Create Backup") },
                        onClick = {
                            onCreateBackup()
                            showMenu = false
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = null
                            )
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Restore Backup") },
                        onClick = {
                            onRestoreBackup()
                            showMenu = false
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarQuotesContent(
    quote: Quote?,
    onBack: () -> Unit,
    onDelete: (Quote) -> Unit,
    onEdit: (Quote) -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text("Quotation Details")
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back to quotations"
                )
            }
        },
        actions = {
            IconButton(onClick = {
                quote?.let {
                    onEdit(it)
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Quotation..."
                )
            }
            IconButton(onClick = {
                quote?.let {
                    onDelete(it)
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Quotation..."
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarQuotesBySubject(
    subject: String,
    onBackClick: () -> Unit,
    onAddClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(subject)
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back to Subjects"
                )
            }
        },
        actions = {
            IconButton(
                onAddClick
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Quotation..."
                )
            }
        }
    )
}

@Composable
fun AddQuoteBottomSheet(
    viewModel: QuotesViewModel,
    onQuoteAdded: (Quote) -> Unit
) {
    val quoteText by viewModel.quoteText.collectAsStateWithLifecycle()
    val author by viewModel.author.collectAsStateWithLifecycle()
    val reference by viewModel.reference.collectAsStateWithLifecycle()
    val subject by viewModel.subject.collectAsStateWithLifecycle()

    val isQuoteError by viewModel.isQuoteError.collectAsStateWithLifecycle()
    val isAuthorError by viewModel.isAuthorError.collectAsStateWithLifecycle()
    val isReferenceError by viewModel.isReferenceError.collectAsStateWithLifecycle()
    val isSubjectError by viewModel.isSubjectError.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .navigationBarsPadding()
            .statusBarsPadding()
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Add New Quote", style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
                .testTag("quote_input"),
            minLines = 3,
            value = quoteText,
            label = { Text("Quote") },
            onValueChange = { viewModel.updateQuoteText(it) },
            isError = isQuoteError,
            supportingText = {
                if (isQuoteError) {
                    Text(
                        text = "Quote cannot be empty",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
                .testTag("author_input"),
            singleLine = true,
            value = author,
            label = { Text("Author") },
            onValueChange = { viewModel.updateAuthor(it) },
            isError = isAuthorError,
            supportingText = {
                if (isAuthorError) {
                    Text(
                        text = "Author cannot be empty",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
                .testTag("reference_input"),
            singleLine = true,
            value = reference,
            label = { Text("Reference") },
            onValueChange = { viewModel.updateReference(it) },
            isError = isReferenceError,
            supportingText = {
                if (isReferenceError) {
                    Text(
                        text = "Reference cannot be empty",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
                .testTag("subject_input"),
            singleLine = true,
            value = subject,
            label = { Text("Subject") },
            onValueChange = { viewModel.updateSubject(it) },
            isError = isSubjectError,
            supportingText = {
                if (isSubjectError) {
                    Text(
                        text = "Subject cannot be empty",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
        Button(
            onClick = {
                viewModel.validateAndGetQuote()?.let {
                    onQuoteAdded(it)
                }
            },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            Text("Save Quote")
        }
    }
}

@Composable
fun EditQuoteBottomSheet(
    viewModel: QuotesViewModel,
    onQuoteEdited: (Quote) -> Unit,
    quote: Quote?
) {
    val quoteText by viewModel.quoteText.collectAsStateWithLifecycle()
    val author by viewModel.author.collectAsStateWithLifecycle()
    val reference by viewModel.reference.collectAsStateWithLifecycle()
    val subject by viewModel.subject.collectAsStateWithLifecycle()

    val isQuoteError by viewModel.isQuoteError.collectAsStateWithLifecycle()
    val isAuthorError by viewModel.isAuthorError.collectAsStateWithLifecycle()
    val isReferenceError by viewModel.isReferenceError.collectAsStateWithLifecycle()
    val isSubjectError by viewModel.isSubjectError.collectAsStateWithLifecycle()

    // Initialize form with quote data if it's the first time
    remember(quote) {
        quote?.let { viewModel.loadQuote(it) }
        true
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .navigationBarsPadding()
            .statusBarsPadding()
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Edit Quote", style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
                .testTag("quote_input"),
            minLines = 3,
            value = quoteText,
            label = { Text("Quote") },
            onValueChange = { viewModel.updateQuoteText(it) },
            isError = isQuoteError,
            supportingText = {
                if (isQuoteError) {
                    Text(
                        text = "Quote cannot be empty",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
                .testTag("author_input"),
            singleLine = true,
            value = author,
            label = { Text("Author") },
            onValueChange = { viewModel.updateAuthor(it) },
            isError = isAuthorError,
            supportingText = {
                if (isAuthorError) {
                    Text(
                        text = "Author cannot be empty",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
                .testTag("reference_input"),
            singleLine = true,
            value = reference,
            label = { Text("Reference") },
            onValueChange = { viewModel.updateReference(it) },
            isError = isReferenceError,
            supportingText = {
                if (isReferenceError) {
                    Text(
                        text = "Reference cannot be empty",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
                .testTag("subject_input"),
            singleLine = true,
            value = subject,
            label = { Text("Subject") },
            onValueChange = { viewModel.updateSubject(it) },
            isError = isSubjectError,
            supportingText = {
                if (isSubjectError) {
                    Text(
                        text = "Subject cannot be empty",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
        Button(
            onClick = {
                viewModel.validateAndGetQuote(quote?.id)?.let {
                    onQuoteEdited(it)
                }
            },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            Text("Update Quote")
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete Quotation") },
        text = { Text("Are you sure you want to delete this quote? This action cannot be undone.") },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text("Delete", color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
