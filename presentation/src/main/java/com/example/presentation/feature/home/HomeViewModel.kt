package com.example.presentation.feature.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.domain.model.BookmarkItem
import com.example.domain.usecase.GetSearchResultUseCase
import com.example.domain.usecase.RemoveBookmarkUseCase
import com.example.domain.usecase.SaveBookmarkUseCase
import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.SearchItemModel
import com.example.presentation.model.toSearchItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.withIndex
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchResultsUseCase: GetSearchResultUseCase,
    private val saveBookmarkUseCase: SaveBookmarkUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase
) : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    //PagingData의 특성상 MVI State에 직접 포함시키기 어려워 별도 Flow로 관리
    val searchResult: MutableStateFlow<PagingData<SearchItemModel>> = MutableStateFlow(PagingData.empty())

    private val _searchQueryFlow = MutableSharedFlow<String>()

    init {
        setupSearchQueryFlow()
    }

    @OptIn(FlowPreview::class)
    private fun setupSearchQueryFlow() {
        viewModelScope.launch {
            _searchQueryFlow
                .debounce(300) // 300ms 동안 새 이벤트가 없으면 다음 단계로 진행
                .filter { it.length >= 2 } // 2글자 이상일 때만 검색 실행
                .distinctUntilChanged() // 이전과 동일한 쿼리는 무시
                .collect { query ->
                    getSearchResult(query)
                }
        }
    }

    override fun createInitialState(): HomeContract.State = HomeContract.State.initial()

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnSearchKeywordChanged -> {
                viewModelScope.launch {
                    setState { this.copy(searchQuery = event.query) }
                    _searchQueryFlow.emit(event.query)
                }
            }

            is HomeContract.Event.OnSearch -> {
                getSearchResult(event.query)
            }

            is HomeContract.Event.OnClickBookmark -> {
                viewModelScope.launch {
                    val item = event.searchItemModel
                    try {
                        // 북마크 상태 토글
                        val newBookmarkState = !item.bookMark


                        // 북마크 상태에 따라 저장 또는 삭제
                        if (item.bookMark) {
                            removeBookmarkUseCase(BookmarkItem(item.url))
                        } else {
                            saveBookmarkUseCase(BookmarkItem(item.url))
                        }

                        // 현재 PagingData의 해당 아이템만 업데이트
                        updateBookmarkState(item, newBookmarkState)

                        Log.d("taek", "북마크 상태 변경: ${item.url}, 새 상태: $newBookmarkState")
                    } catch (e: Exception) {
                        Log.e("taek", "북마크 업데이트 실패: ${e.message}", e)
                        setState { copy(error = e) }
                    }
                }
            }
        }
    }

    private fun updateBookmarkState(item: SearchItemModel, newBookmarkState: Boolean) {
        viewModelScope.launch {
            try {
                // 현재 PagingData 가져오기
                val currentPagingData = searchResult.value

                // 새로운 PagingData 생성
                val updatedPagingData = currentPagingData.map { searchItem ->
                    if (searchItem.url == item.url) {
                        // 북마크 상태만 변경된 새 객체 반환
                        searchItem.copy(bookMark = newBookmarkState)
                    } else {
                        searchItem
                    }
                }

                // 새 PagingData로 UI 갱신
                searchResult.emit(updatedPagingData)

                Log.d("taek", "북마크 상태 업데이트 완료: ${item.url}")
            } catch (e: Exception) {
                Log.e("taek", "북마크 업데이트 중 오류: ${e.message}", e)
                setState { copy(error = e) }
            }
        }
    }

    private fun getSearchResult(query: String) {
        viewModelScope.launch {
            try {
                setState { copy(isLoading = true) }

                // Flow를 한 번만 수집하고 cachedIn으로 공유 가능하게 함
                searchResultsUseCase(query)
                    .withIndex()
                    .collect { (idx, resultFlow) ->
                        // 각 인덱스에 대해 별도의 launch 블록으로 처리
                        launch {
                            try {
                                val cachedFlow = resultFlow.cachedIn(viewModelScope)

                                when (idx) {
                                    0 -> {
                                        Log.d("taek", "collect 초기 결과")
                                        setState { copy(isLoading = false) }
                                    }

                                    1 -> {
                                        Log.d("taek", "collect 전체 결과")
                                    }
                                }

                                // Flow 수집하여 UI 업데이트
                                cachedFlow.collectLatest { pagingData ->
                                    val mappedResults = pagingData.map { it.toSearchItemModel() }
                                    searchResult.emit(mappedResults)
                                }
                            } catch (e: Exception) {
                                Log.e("taek", "결과 처리 오류(idx=$idx): ${e.message}", e)
                                if (idx == 0) {
                                    setState { copy(isLoading = false, error = e) }
                                }
                            }
                        }
                    }
            } catch (exception: Exception) {
                Log.e("taek", "getSearchResult: ${exception.message}")
                setState { copy(isLoading = false, error = exception) }
            }
        }
    }
}