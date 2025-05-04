package com.example.presentation.feature.search.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.presentation.model.SearchResultModel

@Composable
fun SearchItemCard(
    item: SearchResultModel,
    onBookmarkClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            ItemImage(url = item.url)

            Box(modifier = Modifier.align(Alignment.TopEnd)) {
                BookmarkIcon(
                    isBookmarked = item.bookMark,
                    onClick = onBookmarkClick
                )
            }

            Box(modifier = Modifier.align(Alignment.BottomStart)) {
                DateTimeInfo(
                    date = item.date,
                    time = item.time
                )
            }
        }
    }
}