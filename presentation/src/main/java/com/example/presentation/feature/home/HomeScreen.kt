package com.example.presentation.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.domain.model.SearchItem
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey

@Composable
fun HomeScreen(
    padding: PaddingValues,
    onShowErrorSnackBar: (throwable: Throwable) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val searchList = state.searchResult.collectAsLazyPagingItems()
    val query by state.searchQuery.collectAsStateWithLifecycle()
    val gridState = rememberLazyGridState()

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
                    viewModel.setEvent(HomeContract.Event.OnSearchQueryChanged(it))
                },
                placeholder = { Text("검색어를 입력하세요") },
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 그리드 리스트
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                state = gridState
            ) {
                items(
                    count = searchList.itemCount,
                    key = searchList.itemKey { it.url }
                ) { index ->

                    searchList[index]?.let {
                        SearchItemCard(
                            item = it,
                            onBookmarkClick = {

                            }
                        )
                    }
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
    item: SearchItem,
    isBookmarked: Boolean = false,
    onBookmarkClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            // 이미지 (URL이 이미지 URL이라고 가정)
            AsyncImage(
                model = item.url,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // URL 텍스트
            Text(
                text = item.url,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // 북마크 아이콘
            IconButton(
                onClick = onBookmarkClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    imageVector = if (isBookmarked) Icons.Default.Favorite else Icons.Default.Clear,
                    contentDescription = if (isBookmarked) "북마크 취소" else "북마크 추가"
                )
            }
        }
    }
}