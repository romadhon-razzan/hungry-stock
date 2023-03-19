package id.co.ptn.hungrystock.helper.extension

import android.util.Log

fun Any?.printToLog(tag: String = "DEBUG_LOG") {
    Log.d(tag, toString())
}