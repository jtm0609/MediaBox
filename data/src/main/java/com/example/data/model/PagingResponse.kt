package com.example.data.model

import com.example.domain.model.SearchItem
import java.lang.Exception

data class PagingResponse(
    val searchResults: List<SearchItem>
)
