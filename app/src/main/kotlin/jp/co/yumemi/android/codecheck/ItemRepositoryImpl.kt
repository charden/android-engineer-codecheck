package jp.co.yumemi.android.codecheck

import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(private val remoteDataSource: ItemRemoteDataSource): ItemRepository {
    override suspend fun fetchRepositories(inputText: String): List<Item> {
        return remoteDataSource.fetchRepositories(inputText)
    }
}

interface ItemRepository {
    suspend fun fetchRepositories(inputText: String): List<Item>
}