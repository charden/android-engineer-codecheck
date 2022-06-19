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
    @Test
    fun kotlinで検索したときに正常に返り値が得られること() {
        val viewModel = getViewModel("kotlin.json")
        val item = listOf(
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
        runBlocking {
            val result = viewModel.searchResults("kotlin")
            assertEquals(result, item)
        }
    }

    @Test
    fun 空文字で検索したときに正常に返り値が得られること() {
        val viewModel = getViewModel("kotlin.json")
        runBlocking {
            val result = viewModel.searchResults("")
            assertEquals(result, listOf<Item>())
        }
        val client = HttpClient(mockEngine)
        val viewModel = SearchViewModel(client)
        runBlocking {
            val result = viewModel.searchResults("")
            assertEquals(result, listOf<Item>())
        }
    }
}