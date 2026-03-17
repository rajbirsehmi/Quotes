package com.creative.quotes.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.creative.quotes.domain.model.Quote

@Composable
fun QuotationCard(
    quote: Quote,
    onQuoteClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp, end = 8.dp, start = 8.dp)
            .fillMaxWidth()
            .clickable { onQuoteClick(quote.id ?: 0) }
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = quote.quote,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 4.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "-- ${quote.author}, ",
                    style = MaterialTheme.typography.bodyMedium,
                    minLines = 1
                )
                Text(
                    text = quote.reference,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    minLines = 1
                )
            }
        }
    }
}

@Composable
@Preview(
    device = "spec:width=1440px,height=3120px,dpi=560,cutout=punch_hole",
    uiMode = Configuration.UI_MODE_TYPE_NORMAL,
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
            .padding(8.dp)
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
    Card(
        modifier = Modifier
            .padding(top = 8.dp, end = 8.dp, start = 8.dp)
            .fillMaxWidth()
            .clickable { onSubjectClick(subject) }
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = subject,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
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
                    .fillMaxWidth(),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "— ${it.author}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun QuotationDetailsPreview() {
    val quote: Quote = Quote(
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
    onAddClick: () -> Unit
) {
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
                    imageVector = Icons.Default.ArrowBack,
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
fun TopBarAllSubjects(
    onAddClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text("Quotations")
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
                    imageVector = Icons.Default.ArrowBack,
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
    onQuoteAdded: (Quote) -> Unit
) {
    var quoteText by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var reference by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var timeStamp by remember { mutableLongStateOf(0) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .navigationBarsPadding()
            .statusBarsPadding()
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var isQuoteError by remember { mutableStateOf(false) }
        var isAuthorError by remember { mutableStateOf(false) }
        var isReferenceError by remember { mutableStateOf(false) }
        var isSubjectError by remember { mutableStateOf(false) }

        Text("Add New Quote", style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth(),
            minLines = 3,
            value = quoteText,
            label = { Text("Quote") },
            onValueChange = {
                quoteText = it
                if (it.isNotBlank()) isQuoteError = false // Clear error when typing
            },
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
                .fillMaxWidth(),
            singleLine = true,
            value = author,
            label = { Text("Author") },
            onValueChange = {
                author = it
                if (it.isNotBlank()) isAuthorError = false
            },
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
                .fillMaxWidth(),
            singleLine = true,
            value = reference,
            label = { Text("Reference") },
            onValueChange = {
                reference = it
                if (it.isNotBlank()) isReferenceError = false
            },
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
                .fillMaxWidth(),
            singleLine = true,
            value = subject,
            label = { Text("Subject") },
            onValueChange = {
                subject = it
                if (it.isNotBlank()) isSubjectError = false
            },
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
                isQuoteError = quoteText.isBlank()
                isAuthorError = author.isBlank()
                isReferenceError = reference.isBlank()
                isSubjectError = subject.isBlank()

                if (!isQuoteError && !isAuthorError && !isReferenceError && !isSubjectError) {
                    timeStamp = System.currentTimeMillis()
                    onQuoteAdded(Quote(null, quoteText, author, reference, subject, timeStamp))
                    quoteText = ""
                    author = ""
                    reference = ""
                    subject = ""
                    timeStamp = 0
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
    onQuoteEdited: (Quote) -> Unit,
    quote: Quote?
) {
    var quoteText by remember { mutableStateOf(quote!!.quote) }
    var author by remember { mutableStateOf(quote!!.author) }
    var reference by remember { mutableStateOf(quote!!.reference) }
    var subject by remember { mutableStateOf(quote!!.subject) }
    var timeStamp by remember { mutableLongStateOf(quote!!.timestamp) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .navigationBarsPadding()
            .statusBarsPadding()
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var isQuoteError by remember { mutableStateOf(false) }
        var isAuthorError by remember { mutableStateOf(false) }
        var isReferenceError by remember { mutableStateOf(false) }
        var isSubjectError by remember { mutableStateOf(false) }

        Text("Edit Quote", style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth(),
            minLines = 3,
            value = quoteText,
            label = { Text("Quote") },
            onValueChange = {
                quoteText = it
                if (it.isNotBlank()) isQuoteError = false // Clear error when typing
            },
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
                .fillMaxWidth(),
            singleLine = true,
            value = author,
            label = { Text("Author") },
            onValueChange = {
                author = it
                if (it.isNotBlank()) isAuthorError = false
            },
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
                .fillMaxWidth(),
            singleLine = true,
            value = reference,
            label = { Text("Reference") },
            onValueChange = {
                reference = it
                if (it.isNotBlank()) isReferenceError = false
            },
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
                .fillMaxWidth(),
            singleLine = true,
            value = subject,
            label = { Text("Subject") },
            onValueChange = {
                subject = it
                if (it.isNotBlank()) isSubjectError = false
            },
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
                isQuoteError = quoteText.isBlank()
                isAuthorError = author.isBlank()
                isReferenceError = reference.isBlank()
                isSubjectError = subject.isBlank()

                if (!isQuoteError && !isAuthorError && !isReferenceError && !isSubjectError) {
                    timeStamp = System.currentTimeMillis()
                    onQuoteEdited(
                        Quote(
                            quote!!.id,
                            quoteText,
                            author,
                            reference,
                            subject,
                            timeStamp
                        )
                    )
                    quoteText = ""
                    author = ""
                    reference = ""
                    subject = ""
                    timeStamp = 0
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