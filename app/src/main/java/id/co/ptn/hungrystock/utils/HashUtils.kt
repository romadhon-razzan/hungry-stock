package id.co.ptn.hungrystock.utils

import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class HashUtils {
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