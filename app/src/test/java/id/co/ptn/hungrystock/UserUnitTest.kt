package id.co.ptn.hungrystock

import id.co.ptn.hungrystock.utils.*
import org.junit.Assert
import org.junit.Test

class UserUnitTest {
    @Test
    fun check_expired_true() {
        val date1 = stringToDate(yyyyMMdd, "2023-01-28T12:32:42.000000Z")
        val result = date1AfterDate2(dateToString(yyyyMMdd, date1), currentDateString(yyyyMMdd))
        Assert.assertTrue(result)
    }

    @Test
    fun check_expired_false() {
        val date1 = stringToDate(yyyyMMdd, "2023-03-21T12:32:42.000000Z")
        val result = date1AfterDate2(dateToString(yyyyMMdd, date1), currentDateString(yyyyMMdd))
        Assert.assertFalse(result)
    }

    @Test
    fun check_expired_empty() {
        val date1 = stringToDate(yyyyMMdd, "")
        val result = date1AfterDate2(dateToString(yyyyMMdd, date1), currentDateString(yyyyMMdd))
        Assert.assertTrue(result)
    }

    @Test
    fun check_expired_null() {
        val date1 = stringToDate(yyyyMMdd, "fsibjv nv nf vfv")
        val result = date1AfterDate2(dateToString(yyyyMMdd, date1), currentDateString(yyyyMMdd))
        Assert.assertTrue(result)
    }
}