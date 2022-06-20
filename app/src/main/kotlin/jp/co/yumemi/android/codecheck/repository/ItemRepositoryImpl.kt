package jp.co.yumemi.android.codecheck

import jp.co.yumemi.android.codecheck.data.remote.ItemRemoteDataSource
import jp.co.yumemi.android.codecheck.model.Item
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(private val remoteDataSource: ItemRemoteDataSource): ItemRepository {
    override suspend fun fetchRepositories(inputText: String): List<Item> {
        return remoteDataSource.fetchRepositories(inputText)
    }
}

interface ItemRepository {
    suspend fun fetchRepositories(inputText: String): List<Item>
}