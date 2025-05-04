package com.example.presentation.feature.search.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.presentation.R
import com.example.presentation.theme.Black
import com.example.presentation.theme.White
import com.example.presentation.theme.Yellow

@Composable
fun BookmarkIcon(
    isBookmarked: Boolean,
    onClick: () -> Unit
) {
    Icon(
        imageVector = Icons.Filled.Star,
        contentDescription = stringResource(R.string.bookmark_description),
        modifier = Modifier
            .padding(8.dp)
            .size(24.dp)
            .background(
                color = Black.copy(alpha = 0.5f),
                shape = MaterialTheme.shapes.small
            )
            .padding(2.dp)
            .clickable { onClick() },
        tint = if (isBookmarked) Yellow else White.copy(alpha = 0.3f)
    )
}

@Preview(name = "북마크 선택 상태", showBackground = true)
@Composable
fun BookmarkedIconPreview() {
    BookmarkIcon(
        isBookmarked = true,
        onClick = {}
    )
}

@Preview(name = "북마크 미 선택 상태", showBackground = true)
@Composable
fun NotBookmarkedIconPreview() {
    BookmarkIcon(
        isBookmarked = false,
        onClick = {}
    )
}