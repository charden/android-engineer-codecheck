package jp.co.yumemi.android.codecheck

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ItemRemoteDataSource(private val api: Api, private val ioDispatcher: CoroutineDispatcher) {
    suspend fun fetchRepositories(inputText: String): List<Item> = withContext(ioDispatcher) {
        api.fetchRepositories(inputText)
    }
}