/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck

import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.parcelize.Parcelize
import org.json.JSONObject

/**
 * 検索画面用のViewModel
 *
 * @param context Context コンテキスト
 */
class SearchViewModel(
    val context: Context
) : ViewModel() {

    /**
     * GitHubのAPIからレポジトリを検索
     *
     * @param inputText String 検索する文字列
     *
     * @return  List<Item> レポジトリ検索結果
     */
    fun searchResults(inputText: String): List<Item> = runBlocking {
        val client = HttpClient(Android)

        return@runBlocking GlobalScope.async {
            val response: HttpResponse = client.get("https://api.github.com/search/repositories") {
                header("Accept", "application/vnd.github.v3+json")
                parameter("q", inputText)
            }

            val jsonBody = JSONObject(response.receive<String>())

            val jsonItems = jsonBody.optJSONArray("items")

            val items = mutableListOf<Item>()

            if (jsonItems == null) return@async items.toList()

            /**
             * jsonItemからItemのListに変換する
             */
            for (i in 0 until jsonItems.length()) {
                val jsonItem = jsonItems.optJSONObject(i) ?: continue

                val name = jsonItem.optString("full_name")
                val ownerIconUrl = jsonItem.optJSONObject("owner")?.optString("avatar_url") ?: ""
                val language = jsonItem.optString("language")
                val stargazersCount = jsonItem.optLong("stargazers_count")
                val watchersCount = jsonItem.optLong("watchers_count")
                val forksCount = jsonItem.optLong("forks_conut")
                val openIssuesCount = jsonItem.optLong("open_issues_count")

                val item = Item(
                    name = name,
                    ownerIconUrl = ownerIconUrl,
                    language = context.getString(R.string.written_language, language),
                    stargazersCount = stargazersCount,
                    watchersCount = watchersCount,
                    forksCount = forksCount,
                    openIssuesCount = openIssuesCount
                )

                items.add(item)
            }

            return@async items.toList()
        }.await()
    }
}

@Parcelize
data class Item(
    val name: String,
    val ownerIconUrl: String,
    val language: String,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable