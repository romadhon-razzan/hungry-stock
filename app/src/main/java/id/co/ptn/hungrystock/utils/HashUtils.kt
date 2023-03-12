package id.co.ptn.hungrystock.utils

import id.co.ptn.hungrystock.config.ENV
import id.co.ptn.hungrystock.core.network.CUSTOMER_LOGIN
import id.co.ptn.hungrystock.core.network.OTP
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class HashUtils {

    companion object {
        fun hash256Otp(): String{
            return HashUtils().generateHmac256("${ENV.serviceUrl()}$OTP", ENV.serviceSecretKey().toByteArray()) ?: ""
        }

        fun hash256CustomerLogin(): String{
            return HashUtils().generateHmac256("${ENV.serviceUrl()}$CUSTOMER_LOGIN", ENV.serviceSecretKey().toByteArray()) ?: ""
        }
    }
    @Throws(InvalidKeyException::class, NoSuchAlgorithmException::class)
    fun generateHmac256(message: String, key: ByteArray?): String? {
        val bytes = hmac("HmacSHA256", key, message.toByteArray())
        return bytesToHex(bytes)
    }

    @Throws(NoSuchAlgorithmException::class, InvalidKeyException::class)
    fun hmac(algorithm: String?, key: ByteArray?, message: ByteArray?): ByteArray {
        val mac: Mac = Mac.getInstance(algorithm)
        mac.init(SecretKeySpec(key, algorithm))
        return mac.doFinal(message)
    }

    fun bytesToHex(bytes: ByteArray): String? {
        val hexArray = "0123456789abcdef".toCharArray()
        val hexChars = CharArray(bytes.size * 2)
        var j = 0
        var v: Int
        while (j < bytes.size) {
            v = bytes[j].toInt() and 0xFF
            hexChars[j * 2] = hexArray[v ushr 4]
            hexChars[j * 2 + 1] = hexArray[v and 0x0F]
            j++
        }
        return String(hexChars)
    }


}