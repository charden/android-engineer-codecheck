/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 検索画面用のViewModel
 *
 */
@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: ItemRepositoryImpl) : ViewModel() {

    private val _result = MutableStateFlow<List<Item>>(emptyList())
    val result: StateFlow<List<Item>> = _result

    /**
     * GitHubのAPIからレポジトリを検索
     *
     * @param inputText String 検索する文字列
     *
     * @return  List<Item> レポジトリ検索結果
     */
    fun search(inputText: String) {
        if (inputText == "") return

        viewModelScope.launch {
            _result.value = repository.fetchRepositories(inputText)
        }
    }
}