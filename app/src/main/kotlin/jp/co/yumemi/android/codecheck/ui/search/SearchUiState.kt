package jp.co.yumemi.android.codecheck.ui.search

import jp.co.yumemi.android.codecheck.model.Item

sealed class SearchUiState {
    object Loading : SearchUiState()
    object EmptyInput : SearchUiState()
    data class Success(val data: List<Item>) : SearchUiState()
    data class Failure(val e: Throwable) : SearchUiState()
}