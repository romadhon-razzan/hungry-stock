package id.co.ptn.hungrystock.helper.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(format: String = "yyyy-MM-dd HH:mm:ss"): Date? {
    val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
    return dateFormatter.parse(this)
}

fun Long.toDate(format: String = "yyyy-MM-dd HH:mm:ss"): String? {
    val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault() )
    return simpleDateFormat.format(this)
}

fun Date.toStringFormat(format: String = "yyyy-MM-dd HH:mm:ss"): String {
    val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
    return dateFormatter.format(this)
}