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

    @Test
    fun generateHash256Customer() {
        val resultOtp = HashUtils.hash256Otp()
        println("OTP : $resultOtp")
        // 75346e7acda499ee9b025335aa66d3a9c0d36951425286ac94569024f8f4ad2d.8000.5bb112d489e4ec3836d80f6bc6d9ef90

        val result = HashUtils.hash256Profile("code=CO00916")
        println("Customer : $result")
        //500c2ddf77a82bfa81fc4535a53d3212c73485231f6a8a0f9443e49641dfec35.8000.5bb112d489e4ec3836d80f6bc6d9ef90
    }

    @Test
    fun generateHash256Login() {
        var result = HashUtils.hash256CustomerLogin()
        println("Login: $result")
        //500c2ddf77a82bfa81fc4535a53d3212c73485231f6a8a0f9443e49641dfec35.8000.5bb112d489e4ec3836d80f6bc6d9ef90

        result = HashUtils.hash256CheckUserLogin("customer_id=HS00350&ip_address=127.0.0.1")
        println("Is Login: $result")
    }

}