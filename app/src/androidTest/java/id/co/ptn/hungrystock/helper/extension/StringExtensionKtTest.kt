package id.co.ptn.hungrystock.helper.extension

import org.junit.Assert.*
import org.junit.Test

class StringExtensionKtTest {
    @Test
    fun inValidUrl() {
        val url = "http://www.hungrystock.com"
        assertTrue(url.isValidUrl())
    }
}