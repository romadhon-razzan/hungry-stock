package id.co.ptn.hungrystock.utils

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import id.co.ptn.hungrystock.R

class MediaUtils (val context: Context){
    fun setImageFromUrl(imageView: ImageView, url: String) {
        try {
            Glide.with(context)
                .load(url)
                .placeholder(ContextCompat.getDrawable(context, R.drawable.logo_hungry_stock)).into(imageView)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}