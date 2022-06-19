package jp.co.yumemi.android.codecheck

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        val dispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(dispatcher)
        val api = MockApi()
        val dataSource = ItemRemoteDataSource(api, dispatcher)
        val repository = ItemRepositoryImpl(dataSource)
        viewModel = SearchViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun kotlinで検索したときに正常に返り値が得られること() {
        runTest {
            viewModel.search("kotlin")
            assertEquals(viewModel.result.value, MockApi().baseItems)
        }
    }


    @Test
    fun 空文字で検索したときに正常に返り値が得られること() {
        runTest {
            viewModel.search("")
            assertEquals(viewModel.result.value, emptyList<Item>())
        }
    }
}