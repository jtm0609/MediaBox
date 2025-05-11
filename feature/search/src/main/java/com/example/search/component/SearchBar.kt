package com.example.search.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.search.R
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(FlowPreview::class)
@Composable
fun SearchBar(
    onSearchKeyword: (String) -> Unit
) {
    var keywordState by rememberSaveable { mutableStateOf("") }
    var previousKeywordState by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        snapshotFlow { keywordState }
            .debounce(500)
            .distinctUntilChanged()
            .collect { keyword ->
                if (keyword != previousKeywordState) {
                    previousKeywordState = keyword
                    onSearchKeyword(keyword)
                }
            }
    }

    OutlinedTextField(
        value = keywordState,
        onValueChange = {
            keywordState = it
        },
        placeholder = { Text(stringResource(R.string.search_guide_message)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    SearchBar(
        onSearchKeyword = {}
    )
}