package id.co.ptn.hungrystock

import id.co.ptn.hungrystock.utils.*
import org.junit.Assert
import org.junit.Test

class HashUnitTest {
    @Test
    fun generateHash256() {
        val valueToDigest = "https://hs.wintera.co.id/api/v3/otp/userkey=8000"
        val key = "AAACr_y89IrkU9DYfOD7o".toByteArray()
        val hashUtils = HashUtils()
        val result = hashUtils.generateHmac256(valueToDigest, key)
        println(result)
    }

}