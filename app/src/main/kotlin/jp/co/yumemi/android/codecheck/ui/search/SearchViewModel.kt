/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.codecheck.repository.ItemRepositoryImpl
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 検索画面用のViewModel
 *
 */
@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: ItemRepositoryImpl) :
    ViewModel() {

    private val _result = MutableStateFlow<SearchUiState>(SearchUiState.Success(emptyList()))
    val result: StateFlow<SearchUiState> = _result

    /**
     * inputTextで検索する
     *
     * @param inputText String 検索する文字列
     *
     */
    fun search(inputText: String) {
        if (inputText == "") {
            _result.value = SearchUiState.EmptyInput
            return
        }
        _result.value = SearchUiState.Loading
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            _result.value = SearchUiState.Failure(throwable)
        }
        viewModelScope.launch(exceptionHandler) {
            _result.value = SearchUiState.Success(repository.fetchRepositories(inputText))
        }
    }
}