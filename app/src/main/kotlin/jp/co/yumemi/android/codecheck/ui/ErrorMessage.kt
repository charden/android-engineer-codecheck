package jp.co.yumemi.android.codecheck.ui

import androidx.annotation.StringRes
import jp.co.yumemi.android.codecheck.R
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketException
import java.net.UnknownHostException

class ErrorMessage(private val throwable: Throwable) {
    @StringRes
    fun getMessageId(): Int = getStringResId(throwable)
    private fun getStringResId(throwable: Throwable): Int = when (throwable) {
        is UnknownHostException, is ConnectException -> R.string.error_network
        is SocketException -> R.string.error_timeout
        is JSONException -> R.string.error_parse
        else -> R.string.error_unknown
    }
}