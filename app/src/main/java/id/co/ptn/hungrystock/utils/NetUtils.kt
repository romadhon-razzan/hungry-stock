package id.co.ptn.hungrystock.utils

import android.content.Context
import android.content.Context.WIFI_SERVICE
import android.net.wifi.WifiManager
import id.co.ptn.hungrystock.helper.extension.printToLog

class NetUtils(val context: Context?) {
    fun getLocalIpAddress(): String? {
        try {

            val wifiManager: WifiManager = context?.getSystemService(WIFI_SERVICE) as WifiManager
            return ipToString(wifiManager.connectionInfo.ipAddress)
        } catch (ex: Exception) {
            ex.toString().printToLog("IP Address")
        }

        return null
    }

    private fun ipToString(i: Int): String {
        return (i and 0xFF).toString() + "." +
                (i shr 8 and 0xFF) + "." +
                (i shr 16 and 0xFF) + "." +
                (i shr 24 and 0xFF)

    }
}