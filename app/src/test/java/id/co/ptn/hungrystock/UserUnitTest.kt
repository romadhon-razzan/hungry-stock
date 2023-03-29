package id.co.ptn.hungrystock

import id.co.ptn.hungrystock.helper.extension.printToLog
import id.co.ptn.hungrystock.helper.extension.toDate
import id.co.ptn.hungrystock.utils.*
import org.junit.Assert
import org.junit.Test

class UserUnitTest {
    @Test
    fun check_expired_true() {
        val date1 = (1678977725L * 1000).toDate(yyyyMMdd_HHmmss)
        println("Expired date: $date1")
        val result = date1AfterDate2(date1, currentDateString(yyyyMMdd_HHmmss), yyyyMMdd_HHmmss)
        println("Expired: $result")
        Assert.assertTrue(result)
    }

    @Test
    fun check_expired_false() {
        val date1 = (1679323325L * 1000).toDate(yyyyMMdd_HHmmss)
        println("Expired date: $date1")
        val result = date1AfterDate2(date1, currentDateString(yyyyMMdd_HHmmss), yyyyMMdd_HHmmss)
        println("Expired: $result")
        Assert.assertFalse(result)
    }

    @Test
    fun check_expired_empty() {
        val date1 = stringToDate(iso8601, "")
        val result = date1AfterDate2(dateToString(yyyyMMdd_HHmmss, date1), currentDateString(yyyyMMdd_HHmmss), yyyyMMdd_HHmmss)
        Assert.assertTrue(result)
    }

    @Test
    fun check_expired_null() {
        val date1 = stringToDate(iso8601, "fsibjv nv nf vfv")
        val result = date1AfterDate2(dateToString(yyyyMMdd_HHmmss, date1), currentDateString(yyyyMMdd_HHmmss), yyyyMMdd_HHmmss)
        Assert.assertTrue(result)
    }
}