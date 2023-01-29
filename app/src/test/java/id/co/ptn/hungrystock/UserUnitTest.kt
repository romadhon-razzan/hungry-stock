package id.co.ptn.hungrystock

import id.co.ptn.hungrystock.utils.*
import org.junit.Assert
import org.junit.Test

class UserUnitTest {
    @Test
    fun check_expired_true() {
        val date1 = stringToDate(iso8601, "2023-01-29T15:37:42.000000Z")
        println("date time : $date1")
        val result = date1AfterDate2(dateToString(yyyyMMdd_HHmmss, date1), currentDateString(yyyyMMdd_HHmmss), yyyyMMdd_HHmmss)
        println("$result")
        Assert.assertTrue(result)
    }

    @Test
    fun check_expired_false() {
        val date1 = stringToDate(iso8601, "2023-03-21T12:32:42.000000Z")
        val result = date1AfterDate2(dateToString(yyyyMMdd_HHmmss, date1), currentDateString(yyyyMMdd_HHmmss), yyyyMMdd_HHmmss)
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