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

fun monthLabel(id: String) : String {
    return when(id){
        "01" -> {"Januari"}
        "02" -> {"Februari"}
        "03" -> {"Maret"}
        "04" -> {"April"}
        "05" -> {"Mei"}
        "06" -> {"Juni"}
        "07" -> {"Juli"}
        "08" -> {"Agustus"}
        "09" -> {"September"}
        "10" -> {"Oktober"}
        "11" -> {"November"}
        "12" -> {"Desember"}
        else -> {""}
    }
}

fun monthList(): MutableList<String>{
    val months: MutableList<String> = mutableListOf()
    months.add("Januari")
    months.add("Februari")
    months.add("Maret")
    months.add("April")
    months.add("Mei")
    months.add("Juni")
    months.add("Juli")
    months.add("Agustus")
    months.add("September")
    months.add("Oktober")
    months.add("November")
    months.add("Desember")
    return months
}

