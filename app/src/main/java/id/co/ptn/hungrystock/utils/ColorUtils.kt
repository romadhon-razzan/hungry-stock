package id.co.ptn.hungrystock.utils

import android.content.Context
import androidx.core.content.ContextCompat

class ColorUtils {
    companion object {
        fun getColor(context: Context, color: Int): Int{
            return ContextCompat.getColor(context, color)
        }
    }
}