package jp.co.yumemi.android.codecheck

interface Api {
    suspend fun fetchRepositories(inputText: String): List<Item>
}