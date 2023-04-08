package id.co.ptn.hungrystock.utils

import android.app.Activity
import android.provider.Settings

class DeviceUtils {
    companion object {
        fun getDeviceId(activity: Activity): String{
            return Settings.Secure.getString(activity.contentResolver, Settings.Secure.ANDROID_ID)
        }
    }
}