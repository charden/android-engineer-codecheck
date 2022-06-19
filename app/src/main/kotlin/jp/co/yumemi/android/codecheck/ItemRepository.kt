package jp.co.yumemi.android.codecheck

import io.ktor.client.*
import org.json.JSONObject

class ItemRepository(private val client: HttpClient) {
    suspend fun fetchRepositories(inputText: String): List<Item> {
        val responseString: String = GitHubApi(client).fetchRepositories(inputText)

        val jsonBody = JSONObject(responseString)

        val jsonItems = jsonBody.optJSONArray("items")

        val items = mutableListOf<Item>()

        if (jsonItems == null) return items.toList()

        /**
         * jsonItemからItemのListに変換する
         */
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