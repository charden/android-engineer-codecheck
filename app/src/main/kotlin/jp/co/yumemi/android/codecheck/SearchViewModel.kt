/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

/**
 * 検索画面用のViewModel
 *
 */
class SearchViewModel(private val repository: ItemRepository) : ViewModel() {

    /**
     * GitHubのAPIからレポジトリを検索
     *
     * @param inputText String 検索する文字列
     *
     * @return  List<Item> レポジトリ検索結果
     */
    fun searchResults(inputText: String): List<Item> = runBlocking {
        if (inputText == "") return@runBlocking mutableListOf<Item>()

        return@runBlocking GlobalScope.async {
            return@async repository.fetchRepositories(inputText)
        }.await()
    }
}