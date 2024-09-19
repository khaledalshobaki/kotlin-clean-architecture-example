package com.sample.rickandmorty.util

import android.accounts.NetworkErrorException
import com.sample.rickandmorty.R
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class ErrorHandler @Inject constructor() {

    /**
     * Maps exceptions to appropriate error messages.
     * This method demonstrates how different types of exceptions might be handled in a real-world scenario.
     *
     * @param exception The Throwable instance representing the error.
     * @return The resource ID of the error message to display.
     */

    fun getErrorMessage(exception: Throwable): Int {
        return when (exception) {
            is NetworkErrorException,
            is UnknownHostException -> R.string.error_network

            is TimeoutException -> R.string.error_timeout
            else -> R.string.error_unknown
        }
    }
}
