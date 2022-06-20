package jp.co.yumemi.android.codecheck

class MockApi: Api {
    val baseItems = listOf(
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
    override suspend fun fetchRepositories(inputText: String): List<Item> {
        return baseItems
    }
}