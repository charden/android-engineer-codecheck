package jp.co.yumemi.android.codecheck

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SearchViewModelTest {
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        val api = MockApi()
        val dataSource = ItemRemoteDataSource(api, Dispatchers.IO)
        val repository = ItemRepositoryImpl(dataSource)
        viewModel = SearchViewModel(repository)
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
        runBlocking {
            val result = viewModel.searchResults("kotlin")
            assertEquals(result, baseItems)
        }
    }

    @Test
    fun 空文字で検索したときに正常に返り値が得られること() {
        runBlocking {
            val result = viewModel.searchResults("")
            assertEquals(result, listOf<Item>())
        }
    }
}