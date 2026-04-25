package com.creative.quotes.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Label
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
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
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onQuoteClick(quote.id ?: 0) }
            .testTag("quote_card"),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            Icon(
                imageVector = Icons.Default.FormatQuote,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = quote.quote,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .testTag("quote_text")
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        modifier = Modifier.testTag("quote_author_text"),
                        text = quote.author,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    if (quote.reference.isNotEmpty()) {
                        Text(
                            modifier = Modifier.testTag("quote_reference_text"),
                            text = quote.reference,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }
            }
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
    OutlinedCard(
        modifier = Modifier
            .testTag("subject_card")
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onSubjectClick(subject) },
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = subject,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
                    .testTag("quote_subject_text")
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Open Subject",
                tint = MaterialTheme.colorScheme.primary
            )
        }
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
            .padding(28.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        quote?.let {
            Icon(
                imageVector = Icons.Default.FormatQuote,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                modifier = Modifier
                    .size(80.dp)
                    .padding(bottom = 16.dp)
            )
            Text(
                text = it.quote,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic,
                    lineHeight = MaterialTheme.typography.headlineLarge.lineHeight * 1.2f
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("quote_text"),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            HorizontalDivider(
                modifier = Modifier
                    .padding(vertical = 32.dp)
                    .width(64.dp),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
            Text(
                modifier = Modifier.testTag("quote_author_text"),
                text = it.author,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            if (it.reference.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .testTag("quote_reference_text"),
                    text = it.reference,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
            if (it.subject.isNotEmpty()) {
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedCard(
                    shape = MaterialTheme.shapes.small,
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        width = 0.5.dp,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                ) {
                    Text(
                        text = it.subject,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                            .testTag("quote_subject_text"),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
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
            Text(
                "Quotations",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.testTag("top_bar_title")
            )
        },
        colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        actions = {
            IconButton(
                onClick = onAddClick,
                modifier = Modifier.testTag("top_bar_add_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Quotation...",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Box {
                IconButton(
                    onClick = { showMenu = true },
                    modifier = Modifier.testTag("top_bar_settings_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    shape = MaterialTheme.shapes.large
                ) {
                    DropdownMenuItem(
                        text = { 
                            Text(
                                "Create Backup",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Medium
                                )
                            ) 
                        },
                        onClick = {
                            onCreateBackup()
                            showMenu = false
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        modifier = Modifier.testTag("top_bar_backup_button")
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                    DropdownMenuItem(
                        text = { 
                            Text(
                                "Restore Backup",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Medium
                                )
                            ) 
                        },
                        onClick = {
                            onRestoreBackup()
                            showMenu = false
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        modifier = Modifier.testTag("top_bar_restore_button")
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
    onEdit: (Quote) -> Unit,
    onShare: (Quote) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    CenterAlignedTopAppBar(
        title = {
            Text(
                "Quotation Details",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.testTag("top_bar_title")
            )
        },
        colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        navigationIcon = {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("top_bar_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back to quotations",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    quote?.let { onShare(it) }
                },
                modifier = Modifier.testTag("top_bar_share_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share Quotation",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Box {
                IconButton(
                    onClick = { showMenu = true },
                    modifier = Modifier.testTag("top_bar_more_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    shape = MaterialTheme.shapes.large
                ) {
                    DropdownMenuItem(
                        modifier = Modifier.testTag("top_bar_edit_button"),
                        text = { 
                            Text(
                                "Edit",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Medium
                                )
//                                modifier = Modifier.testTag("top_bar_edit_button")
                            ) 
                        },
                        onClick = {
                            quote?.let { onEdit(it) }
                            showMenu = false
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                    DropdownMenuItem(
                        modifier = Modifier.testTag("top_bar_delete_button"),
                        text = { 
                            Text(
                                "Delete",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Medium
                                ),
                                color = MaterialTheme.colorScheme.error
//                                modifier = Modifier.testTag("top_bar_delete_button")
                            ) 
                        },
                        onClick = {
                            quote?.let { onDelete(it) }
                            showMenu = false
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(20.dp)
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
fun TopBarQuotesBySubject(
    subject: String,
    onBackClick: () -> Unit,
    onAddClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                subject,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.testTag("top_bar_title")
            )
        },
        colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.testTag("top_bar_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back to Subjects",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        actions = {
            IconButton(
                onClick = onAddClick,
                modifier = Modifier.testTag("top_bar_add_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Quotation...",
                    tint = MaterialTheme.colorScheme.primary
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
    val formState by viewModel.formState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(24.dp)
            .navigationBarsPadding()
            .statusBarsPadding()
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Add New Quote",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.testTag("bottom_sheet_title")
        )
        
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("quote_input"),
            minLines = 3,
            value = formState.quoteText,
            label = { Text("Quote Content") },
            onValueChange = { viewModel.updateQuoteText(it) },
            isError = formState.isQuoteError,
            shape = MaterialTheme.shapes.medium,
            supportingText = {
                if (formState.isQuoteError) {
                    Text(
                        text = "Quote cannot be empty",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.FormatQuote,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                )
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .testTag("author_input"),
                singleLine = true,
                value = formState.author,
                label = { Text("Author") },
                onValueChange = { viewModel.updateAuthor(it) },
                isError = formState.isAuthorError,
                shape = MaterialTheme.shapes.medium,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .testTag("subject_input"),
                singleLine = true,
                value = formState.subject,
                label = { Text("Subject") },
                onValueChange = { viewModel.updateSubject(it) },
                isError = formState.isSubjectError,
                shape = MaterialTheme.shapes.medium,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Label,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            )
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("reference_input"),
            singleLine = true,
            value = formState.reference,
            label = { Text("Reference (Book, Speech, etc.)") },
            onValueChange = { viewModel.updateReference(it) },
            isError = formState.isReferenceError,
            shape = MaterialTheme.shapes.medium,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Book,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        )

        Button(
            onClick = {
                viewModel.validateAndGetQuote()?.let {
                    onQuoteAdded(it)
                }
            },
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .height(56.dp)
                .testTag("bottom_sheet_save_button"),
            shape = MaterialTheme.shapes.large
        ) {
            Icon(Icons.Default.Save, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Save Quote", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun EditQuoteBottomSheet(
    viewModel: QuotesViewModel,
    onQuoteEdited: (Quote) -> Unit,
    quote: Quote?
) {
    val formState by viewModel.formState.collectAsStateWithLifecycle()

    // Initialize form with quote data if it's the first time
    remember(quote) {
        quote?.let { viewModel.loadQuote(it) }
        true
    }

    Column(
        modifier = Modifier
            .padding(24.dp)
            .navigationBarsPadding()
            .statusBarsPadding()
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Edit Quote",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.testTag("bottom_sheet_title")
        )
        
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("quote_input"),
            minLines = 3,
            value = formState.quoteText,
            label = { Text("Quote Content") },
            onValueChange = { viewModel.updateQuoteText(it) },
            isError = formState.isQuoteError,
            shape = MaterialTheme.shapes.medium,
            supportingText = {
                if (formState.isQuoteError) {
                    Text(
                        text = "Quote cannot be empty",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.FormatQuote,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                )
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .testTag("author_input"),
                singleLine = true,
                value = formState.author,
                label = { Text("Author") },
                onValueChange = { viewModel.updateAuthor(it) },
                isError = formState.isAuthorError,
                shape = MaterialTheme.shapes.medium,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .testTag("subject_input"),
                singleLine = true,
                value = formState.subject,
                label = { Text("Subject") },
                onValueChange = { viewModel.updateSubject(it) },
                isError = formState.isSubjectError,
                shape = MaterialTheme.shapes.medium,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Label,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            )
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("reference_input"),
            singleLine = true,
            value = formState.reference,
            label = { Text("Reference") },
            onValueChange = { viewModel.updateReference(it) },
            isError = formState.isReferenceError,
            shape = MaterialTheme.shapes.medium,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Book,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        )

        Button(
            onClick = {
                viewModel.validateAndGetQuote(quote?.id)?.let {
                    onQuoteEdited(it)
                }
            },
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .height(56.dp)
                .testTag("bottom_sheet_edit_button"),
            shape = MaterialTheme.shapes.large
        ) {
            Icon(Icons.Default.Save, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Update Quote", style = MaterialTheme.typography.titleMedium)
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
