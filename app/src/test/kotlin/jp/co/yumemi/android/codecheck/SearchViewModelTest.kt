package jp.co.yumemi.android.codecheck

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.*
import jp.co.yumemi.android.codecheck.helper.JsonLoadHelper
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SearchViewModelTest {
    private fun getViewModel(filename: String): SearchViewModel {
        val mockEngine = MockEngine {
            respond(
                content = ByteReadChannel(JsonLoadHelper.loadJsonAsString(filename)),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val client = HttpClient(mockEngine)
        return SearchViewModel(client)
    }

    private val baseItems = listOf(
        Item(
            "JetBrains/kotlin",
            "https://avatars.githubusercontent.com/u/878437?v=4",
            "Kotlin",
            41802,
            41802,
            5162,
            147
        ),
        Item(
            "hussien89aa/KotlinUdemy",
            "https://avatars.githubusercontent.com/u/7304399?v=4",
            "Kotlin",
            1462,
            1462,
            4936,
            13
        )
    )

    @Test
    fun kotlinで検索したときに正常に返り値が得られること() {
        val viewModel = getViewModel("kotlin.json")

        runBlocking {
            val result = viewModel.searchResults("kotlin")
            assertEquals(result, baseItems)
        }
    }

    @Test
    fun 空文字で検索したときに正常に返り値が得られること() {
        val viewModel = getViewModel("kotlin.json")
        runBlocking {
            val result = viewModel.searchResults("")
            assertEquals(result, listOf<Item>())
        }
    }

    @Test
    fun fullnameがjsonに含まれない時にエラーにならないこと() {
        val viewModel = getViewModel("excludeFullName.json")
        val item = baseItems.map { item -> item.copy(name = "") }
        runBlocking {
            val result = viewModel.searchResults("kotlin")
            assertEquals(result, item)
        }
    }

    @Test
    fun ownerがjsonに含まれない時にエラーにならないこと() {
        val viewModel = getViewModel("excludeOwner.json")
        val items = baseItems.map { item -> item.copy(ownerIconUrl = "") }
        runBlocking {
            val result = viewModel.searchResults("kotlin")
            assertEquals(result, items)
        }
    }

    @Test
    fun languageがjsonに含まれない時にエラーにならないこと() {
        val viewModel = getViewModel("excludeLanguage.json")
        val items = baseItems.map { item -> item.copy(language = "") }
        runBlocking {
            val result = viewModel.searchResults("kotlin")
            assertEquals(result, items)
        }
    }

    @Test
    fun stargazersCountがjsonに含まれない時にエラーにならないこと() {
        val viewModel = getViewModel("excludeStargazersCount.json")
        val items = baseItems.map { item -> item.copy(stargazersCount = 0) }
        runBlocking {
            val result = viewModel.searchResults("kotlin")
            assertEquals(result, items)
        }
    }

    @Test
    fun watchersCountがjsonに含まれない時にエラーにならないこと() {
        val viewModel = getViewModel("excludeWatchersCount.json")
        val items = baseItems.map { item -> item.copy(watchersCount = 0) }
        runBlocking {
            val result = viewModel.searchResults("kotlin")
            assertEquals(result, items)
        }
    }

    @Test
    fun forksCountがjsonに含まれない時にエラーにならないこと() {
        val viewModel = getViewModel("excludeForksCount.json")
        val items = baseItems.map { item -> item.copy(forksCount = 0) }
        runBlocking {
            val result = viewModel.searchResults("kotlin")
            assertEquals(result, items)
        }
    }

    @Test
    fun openIssuesCountがjsonに含まれない時にエラーにならないこと() {
        val viewModel = getViewModel("excludeOpenIssuesCount.json")
        val items = baseItems.map { item -> item.copy(openIssuesCount = 0) }
        runBlocking {
            val result = viewModel.searchResults("kotlin")
            assertEquals(result, items)
        }
    }

    @Test
    fun jsonのItemが空だった場合にエラーにならないこと() {
        val viewModel = getViewModel("emptyItem.json")
        runBlocking {
            val result = viewModel.searchResults("")
            assertEquals(result, listOf<Item>())
        }
    }
}