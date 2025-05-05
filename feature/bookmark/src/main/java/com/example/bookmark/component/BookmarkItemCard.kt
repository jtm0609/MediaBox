package com.example.bookmark.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookmark.model.BookmarkModel
import com.example.core_ui.component.ItemImage

@Composable
fun BookmarkItemCard(
    item: BookmarkModel
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            ItemImage(url = item.url)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookmarkItemCardPreview() {
    BookmarkItemCard(
        item = BookmarkModel("https://search4.kakaocdn.net/argon/138x78_80_pr/E1H7Out9GDz")
    )
}