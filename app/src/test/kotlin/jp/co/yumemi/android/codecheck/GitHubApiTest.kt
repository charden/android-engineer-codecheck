package jp.co.yumemi.android.codecheck

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.*
import jp.co.yumemi.android.codecheck.data.remote.GitHubApi
import jp.co.yumemi.android.codecheck.data.remote.MockApi
import jp.co.yumemi.android.codecheck.helper.JsonLoadHelper
import jp.co.yumemi.android.codecheck.model.Item
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GitHubApiTest {

    private val baseItems = MockApi().baseItems

    private fun getClient(filename: String): HttpClient {
        val mockEngine = MockEngine {
            respond(
                content = ByteReadChannel(JsonLoadHelper.loadJsonAsString(filename)),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        return HttpClient(mockEngine)
    }

    @Test
    fun kotlinで検索したときに正常に返り値が得られること() {
        val client = getClient("kotlin.json")
        val api = GitHubApi(client)
        runBlocking {
            val result = api.fetchRepositories("Kotlin")
            assertEquals(result, baseItems)
        }
    }

    @Test
    fun fullnameがjsonに含まれない時にエラーにならないこと() {
        val client = getClient("excludeFullName.json")
        val api = GitHubApi(client)
        val item = baseItems.map { item -> item.copy(name = "") }
        runBlocking {
            val result = api.fetchRepositories("Kotlin")
            assertEquals(result, item)
        }
    }

    @Test
    fun ownerがjsonに含まれない時にエラーにならないこと() {
        val client = getClient("excludeOwner.json")
        val api = GitHubApi(client)
        val item = baseItems.map { item -> item.copy(ownerIconUrl = "") }
        runBlocking {
            val result = api.fetchRepositories("Kotlin")
            assertEquals(result, item)
        }
    }

    @Test
    fun languageがjsonに含まれない時にエラーにならないこと() {
        val client = getClient("excludeLanguage.json")
        val api = GitHubApi(client)
        val item = baseItems.map { item -> item.copy(language = "") }
        runBlocking {
            val result = api.fetchRepositories("Kotlin")
            assertEquals(result, item)
        }
    }

    @Test
    fun stargazersCountがjsonに含まれない時にエラーにならないこと() {
        val client = getClient("excludeStargazersCount.json")
        val api = GitHubApi(client)
        val item = baseItems.map { item -> item.copy(stargazersCount = 0) }
        runBlocking {
            val result = api.fetchRepositories("Kotlin")
            assertEquals(result, item)
        }
    }

    @Test
    fun watchersCountがjsonに含まれない時にエラーにならないこと() {
        val client = getClient("excludeWatchersCount.json")
        val api = GitHubApi(client)
        val item = baseItems.map { item -> item.copy(watchersCount = 0) }
        runBlocking {
            val result = api.fetchRepositories("Kotlin")
            assertEquals(result, item)
        }
    }

    @Test
    fun forksCountがjsonに含まれない時にエラーにならないこと() {
        val client = getClient("excludeForksCount.json")
        val api = GitHubApi(client)
        val item = baseItems.map { item -> item.copy(forksCount = 0) }
        runBlocking {
            val result = api.fetchRepositories("Kotlin")
            assertEquals(result, item)
        }
    }

    @Test
    fun openIssuesCountがjsonに含まれない時にエラーにならないこと() {
        val client = getClient("excludeOpenIssuesCount.json")
        val api = GitHubApi(client)
        val item = baseItems.map { item -> item.copy(openIssuesCount = 0) }
        runBlocking {
            val result = api.fetchRepositories("Kotlin")
            assertEquals(result, item)
        }
    }

    @Test
    fun jsonのItemが空だった場合にエラーにならないこと() {
        val client = getClient("emptyItem.json")
        val api = GitHubApi(client)
        runBlocking {
            val result = api.fetchRepositories("")
            assertEquals(result, listOf<Item>())
        }
    }
}