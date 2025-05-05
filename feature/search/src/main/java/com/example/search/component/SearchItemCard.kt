package com.example.search.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.component.ItemImage
import com.example.search.model.SearchResultModel

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

@Preview(showBackground = true)
@Composable
fun SearchItemCardPreview() {
    SearchItemCard(
        item = SearchResultModel(
            url = "https://search4.kakaocdn.net/argon/138x78_80_pr/E1H7Out9GDz",
            date = "2023.05.15",
            time = "14:30",
            bookMark = true
        ),
        onBookmarkClick = {}
    )
}