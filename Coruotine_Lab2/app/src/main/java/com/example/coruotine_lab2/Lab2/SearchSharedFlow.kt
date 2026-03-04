import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
@Composable
fun SearchScreen() {
    val names = listOf("Alice", "Bob", "Charlie", "David", "Diana", "Eve", "Frank")

    val searchFlow = remember { MutableSharedFlow<String>() }


    val scope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }
    var filteredNames by remember { mutableStateOf(names) }


    LaunchedEffect(searchFlow) {
        searchFlow.collectLatest { query ->
            filteredNames = if (query.isBlank()) {
                names
            } else {
                names.filter { it.startsWith(query, ignoreCase = true) }
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        TextField(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
                scope.launch { searchFlow.emit(query) }
            },
            label = { Text("Search by first letter") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(filteredNames) { name ->
                Text(text = name, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(8.dp))
            }
        }
    }
}