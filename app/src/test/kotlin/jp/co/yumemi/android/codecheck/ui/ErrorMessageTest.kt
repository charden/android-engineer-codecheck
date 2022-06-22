package jp.co.yumemi.android.codecheck.ui

import jp.co.yumemi.android.codecheck.R
import org.json.JSONException
import org.junit.Assert.*
import org.junit.Test
import java.net.ConnectException
import java.net.SocketException
import java.net.UnknownHostException

class ErrorMessageTest {
    @Test
    fun UnknownHostExceptionが発生したときにerror_networkが返ること() {
        assertEquals(ErrorMessage(UnknownHostException()).getMessageId(), R.string.error_network)
    }

    @Test
    fun ConnectExceptionが発生したときにerror_networkが返ること() {
        assertEquals(ErrorMessage(ConnectException()).getMessageId(), R.string.error_network)
    }

    @Test
    fun SocketExceptionが発生したときにerror_networkが返ること() {
        assertEquals(ErrorMessage(SocketException()).getMessageId(), R.string.error_timeout)
    }

    @Test
    fun JSONExceptionが発生したときにerror_networkが返ること() {
        assertEquals(ErrorMessage(JSONException("parse error")).getMessageId(), R.string.error_parse)
    }

    @Test
    fun 不明なエラーが発生したときにerror_networkが返ること() {
        assertEquals(ErrorMessage(Exception()).getMessageId(), R.string.error_unknown)
    }
}