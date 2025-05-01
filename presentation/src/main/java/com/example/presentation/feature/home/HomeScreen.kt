package com.example.presentation.feature.home

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.VideoLibrary
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.example.domain.model.SearchItemType
import com.example.presentation.model.SearchItemModel

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
    LaunchedEffect(searchList.loadState) {

        when (val currentState = searchList.loadState.refresh) {
            is LoadState.Error -> {
                keyboardController?.hide()
                onShowErrorSnackBar(currentState.error)
            }

            LoadState.Loading -> {
                Log.d("taek", "refresh loading")
                if (searchList.itemCount > 0)
                    viewModel.setEvent(HomeContract.Event.OnSearchLoading)
            }

            is LoadState.NotLoading -> {
                Log.d("taek", "refresh not loading")
                viewModel.setEvent(HomeContract.Event.OnSearchFinish)
            }
        }


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
                    viewModel.setEvent(HomeContract.Event.OnSearchQueryChanged(it))
                },
                placeholder = { Text("검색어를 입력하세요") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 그리드 리스트
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                state = gridState
            ) {
                items(
                    count = searchList.itemCount
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
    item: SearchItemModel,
    isBookmarked: Boolean = false,
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
            // 이미지를 가득 채움
            AsyncImage(
                model = item.url,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )
            
            // 텍스트 가독성을 위한 그라데이션 오버레이
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        color = Color.Black.copy(alpha = 0.7f)
                    )
            )
            
            // 날짜와 시간을 이미지 왼쪽 아래에 표시
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
                    .padding(bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 타입 아이콘 (이미지 또는 비디오)
                Icon(
                    imageVector = if (item.type == SearchItemType.IMAGE) Icons.Default.Image else Icons.Default.VideoLibrary,
                    contentDescription = if (item.type == SearchItemType.IMAGE) "이미지" else "비디오",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Column {
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
}