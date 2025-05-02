package com.example.presentation.feature.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.presentation.model.SearchItemModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@OptIn(FlowPreview::class)
@Composable
fun HomeScreen(
    padding: PaddingValues,
    onShowErrorSnackBar: (throwable: Throwable) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val query by state.searchQuery.collectAsStateWithLifecycle()
    val searchList = state.searchResult.collectAsLazyPagingItems()
    val gridState = rememberLazyGridState()

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(state.searchQuery) {
        state.searchQuery
            .debounce(timeoutMillis = 700L)
            .filter { it.isNotEmpty() && it.length >= 2 }
            .distinctUntilChanged()
            .collect { query ->
                Log.d("taek", "query: $query")
                viewModel.setEvent(HomeContract.Event.OnSearch(query))
            }
    }

    state.error?.let {
        onShowErrorSnackBar(it)
        keyboardController?.hide()
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // 검색바
            OutlinedTextField(
                value = query,
                onValueChange = {
                    viewModel.setEvent(HomeContract.Event.OnSearchKeywordChanged(it))
                },
                placeholder = { Text("검색어를 입력하세요") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 그리드 리스트
            if (query.count() > 1) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    state = gridState
                ) {
                    items(
                        count = searchList.itemCount,
                        key = { index -> 
                            // URL과 날짜, 시간을 조합하여 고유한 키 생성
                            searchList[index]?.let { "${it.url}_${it.date}_${it.time}" } ?: index
                        }
                    ) { index ->
                        val item = searchList[index]
                        item?.let {
                            SearchItemCard(
                                item = it,
                                onBookmarkClick = {
                                    viewModel.setEvent(HomeContract.Event.OnClickBookmark(it))
                                }
                            )
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "검색어를 입력하세요",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            }
        }

        // 로딩 표시
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun SearchItemCard(
    item: SearchItemModel,
    onBookmarkClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            AsyncImage(
                model = item.url,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )

            Log.d("taek", "item.bookMark: ${item.bookMark}")
            // 북마크 아이콘 표시
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "북마크",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(24.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.5f),
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(2.dp)
                    .clickable { 
                        onBookmarkClick() 
                    },
                tint = if (item.bookMark) Color.Yellow else Color.White.copy(alpha = 0.3f)
            )

            // 날짜와 시간을 이미지 왼쪽 아래에 세로로 표시
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.6f),
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            ) {
                Text(
                    text = item.date,
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = item.time,
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}