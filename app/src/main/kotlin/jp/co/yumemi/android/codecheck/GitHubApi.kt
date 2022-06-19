package jp.co.yumemi.android.codecheck

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class GitHubApi(private val client: HttpClient) {
    suspend fun fetchRepositories(inputText: String): String {
        val response: HttpResponse = client.get("https://api.github.com/search/repositories") {
            header("Accept", "application/vnd.github.v3+json")
            parameter("q", inputText)
        }
        return response.receive()
    }
}