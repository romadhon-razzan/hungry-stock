package id.co.ptn.hungrystock.utils

import android.text.format.DateUtils
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

const val indonesianTag = "in-ID"
const val yyyyMMdd = "yyyy-MM-dd"
const val yyyyMMdd_HHmmss = "yyyy-MM-dd HH:mm:ss"
const val iso8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
// Maret, 21 2022
fun getDateMMMMddyyyy(strDate: String): String {
    val stringToDate: Date? = SimpleDateFormat(yyyyMMdd, Locale.forLanguageTag(indonesianTag)).parse(strDate)
    val simpleDateFormat = SimpleDateFormat("MMMM, dd yyyy", Locale.getDefault() )
    return simpleDateFormat.format(stringToDate ?: "")
}

fun getDateMMMMddyyyy(date: Long): String {
    val simpleDateFormat = SimpleDateFormat("MMMM, dd yyyy", Locale.getDefault() )
    return simpleDateFormat.format(date)
}

fun getHHmm(date: Long): String {
    val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault() )
    return simpleDateFormat.format(date)
}

fun stringToDate(format: String, date: String): Date? {
    return try {
        SimpleDateFormat(format, Locale.forLanguageTag(indonesianTag)).parse(date)
    }catch (e: Exception){
        println(e.toString())
        currentDate()
    }
}

fun dateToString(format: String, date: Date?): String? {
    val simpleDateFormat = SimpleDateFormat(format, Locale.forLanguageTag(indonesianTag) )
    return date?.let { simpleDateFormat.format(it) }
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

fun monthListDesc(): MutableList<String>{
    val months: MutableList<String> = mutableListOf()
    months.add("Desember")
    months.add("November")
    months.add("Oktober")
    months.add("September")
    months.add("Agustus")
    months.add("Juli")
    months.add("Juni")
    months.add("Mei")
    months.add("April")
    months.add("Maret")
    months.add("Februari")
    months.add("Januari")
    return months
}

fun date1AfterDate2(date1: String?, date2: String, format: String): Boolean {
    val sdf = SimpleDateFormat(format, Locale.forLanguageTag(indonesianTag))
    val firstDate: Date? = date1?.let { sdf.parse(it) }
    val secondDate: Date? = sdf.parse(date2)

    val cmp = firstDate?.compareTo(secondDate)
    return if (cmp != null){
        when {
            cmp > 0 -> {
                false
            }
            else -> {
                true
            }
        }
    } else {
        false
    }
}

fun currentDateString(format: String): String{
    val time = Calendar.getInstance().time
    val formatter = SimpleDateFormat(format, Locale.forLanguageTag(indonesianTag))
    return formatter.format(time)
}

fun currentDate(): Date {
    return Calendar.getInstance().time
}

