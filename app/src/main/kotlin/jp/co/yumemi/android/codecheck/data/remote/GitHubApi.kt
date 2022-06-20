package jp.co.yumemi.android.codecheck.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.codecheck.model.Item
import org.json.JSONObject
import javax.inject.Inject

class GitHubApi @Inject constructor(private val client: HttpClient) : Api {

    /**
     * GitHub APIで引数に指定された文字列のレポジトリを検索する
     *
     * @param inputText String 検索する文字列
     *
     * @return List<Item>
     */
    override suspend fun fetchRepositories(inputText: String): List<Item> {
        val response: HttpResponse = client.get("https://api.github.com/search/repositories") {
            header("Accept", "application/vnd.github.v3+json")
            parameter("q", inputText)
        }

        return parseJsonString(response.receive())
    }

    /**
     * jsonStringをList<Item>に変換する
     *
     * @param jsonString String Json文字列
     *
     * @return List<Item>
     */
    private fun parseJsonString(jsonString: String): List<Item> {
        val jsonBody = JSONObject(jsonString)

        val jsonItems = jsonBody.optJSONArray("items")

        val items = mutableListOf<Item>()

        if (jsonItems == null) return items.toList()

        for (i in 0 until jsonItems.length()) {
            val jsonItem = jsonItems.optJSONObject(i) ?: continue
            val item = parseItem(jsonItem)
            items.add(item)
        }

        return items.toList()
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