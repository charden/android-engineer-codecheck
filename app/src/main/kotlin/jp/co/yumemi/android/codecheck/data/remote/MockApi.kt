package jp.co.yumemi.android.codecheck.data.remote

import jp.co.yumemi.android.codecheck.model.Item
import org.json.JSONException
import java.net.SocketException
import java.net.UnknownHostException

class MockApi(private val status: Status = Status.Success) : Api {
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

    enum class Status {
        Success,
        UnknownHostException,
        ConnectException,
        SocketException,
        JSONException,
        UNKNOWN
    }

    override suspend fun fetchRepositories(inputText: String): List<Item> {
        return when (status) {
            Status.Success -> baseItems
            Status.UnknownHostException -> throw UnknownHostException()
            Status.ConnectException -> throw UnknownHostException()
            Status.SocketException -> throw SocketException()
            Status.JSONException -> throw JSONException("parse error")
            Status.UNKNOWN -> throw Exception()
        }
    }
}