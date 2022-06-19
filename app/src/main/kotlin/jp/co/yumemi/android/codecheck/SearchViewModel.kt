/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck

import androidx.lifecycle.ViewModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

/**
 * 検索画面用のViewModel
 *
 */
class SearchViewModel(private val client: HttpClient) : ViewModel() {

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
            val responseString: String = GitHubApi(client).fetchRepositories(inputText)

            val jsonBody = JSONObject(responseString)

            val jsonItems = jsonBody.optJSONArray("items")

            val items = mutableListOf<Item>()

            if (jsonItems == null) return@async items.toList()

            /**
             * jsonItemからItemのListに変換する
             */
            for (i in 0 until jsonItems.length()) {
                val jsonItem = jsonItems.optJSONObject(i) ?: continue
                val item = parseItem(jsonItem)
                items.add(item)
            }

            return@async items.toList()
        }.await()
    }

    /**
     * JSONObjectからItemを作成
     *
     * @param jsonItem JSONObject
     *
     * @return Item
     */
    private fun parseItem(jsonItem: JSONObject): Item {
        val name = jsonItem.optString("full_name")
        val ownerIconUrl = jsonItem.optJSONObject("owner")?.optString("avatar_url") ?: ""
        val language = jsonItem.optString("language")
        val stargazersCount = jsonItem.optLong("stargazers_count")
        val watchersCount = jsonItem.optLong("watchers_count")
        val forksCount = jsonItem.optLong("forks_count")
        val openIssuesCount = jsonItem.optLong("open_issues_count")

        return Item(
            name = name,
            ownerIconUrl = ownerIconUrl,
            language = language,
            stargazersCount = stargazersCount,
            watchersCount = watchersCount,
            forksCount = forksCount,
            openIssuesCount = openIssuesCount
        )
    }
}