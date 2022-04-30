package id.co.ptn.hungrystock.utils

import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

// Maret, 21 2022
fun getDateMMMMddyyyy(strDate: String): String {
    val stringToDate: Date = SimpleDateFormat("yyyy-MM-dd").parse(strDate)
    val simpleDateFormat = SimpleDateFormat("MMMM, dd yyyy", Locale.getDefault() )
    return simpleDateFormat.format(stringToDate)
}

fun currentYear() : String {
    val calendar = Calendar.getInstance()
    val year = calendar[Calendar.YEAR]
    return year.toString()
}

fun currentMonth() : String {
    val calendar = Calendar.getInstance()
    val m = calendar[Calendar.MONTH] + 1
    val month = StringBuilder()
    if (m < 10) month.append("0$m") else month.append(m.toString())
    return month.toString()
}