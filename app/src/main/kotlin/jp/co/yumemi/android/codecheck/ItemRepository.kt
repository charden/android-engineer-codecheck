package jp.co.yumemi.android.codecheck

class ItemRepository(private val remoteDataSource: ItemRemoteDataSource) {
    suspend fun fetchRepositories(inputText: String): List<Item> {
        return remoteDataSource.fetchRepositories(inputText)
    }
}