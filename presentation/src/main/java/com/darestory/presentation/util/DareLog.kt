package com.darestory.presentation.util

import timber.log.Timber

object DareLog {

    fun D(msg : String) {
        Timber.tag("Dare Log").d(msg)
    }

    fun D(log : String, msg : String) {
        Timber.tag(log).d(msg)
    }
}