package jp.co.yumemi.android.codecheck.data.remote

import jp.co.yumemi.android.codecheck.model.Item

interface Api {
    suspend fun fetchRepositories(inputText: String): List<Item>
}