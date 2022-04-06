package id.co.ptn.hungrystock.utils

import java.text.SimpleDateFormat
import java.util.*

// Maret, 21 2022
fun getDateMMMMddyyyy(strDate: String): String {
    val stringToDate: Date = SimpleDateFormat("yyyy-MM-dd").parse(strDate)
    val simpleDateFormat = SimpleDateFormat("MMMM, dd yyyy", Locale.getDefault() )
    return simpleDateFormat.format(stringToDate)
}