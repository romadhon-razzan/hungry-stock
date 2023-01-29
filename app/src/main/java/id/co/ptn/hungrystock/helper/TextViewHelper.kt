package id.co.ptn.hungrystock.helper

import android.content.Context
import android.widget.TextView
import id.co.ptn.hungrystock.utils.ColorUtils

class TextViewHelper(val context: Context, val color: Int) {
    private val textViewHelper = TextView(context)
    fun setText(text: String){
        textViewHelper.setTextColor(ColorUtils.getColor(context, color))
        textViewHelper.text = text
        textViewHelper.textSize = 14F
    }
    fun setPadding(start: Int, top: Int, end: Int, bottom: Int) {
        textViewHelper.setPadding(start,top,end,bottom)
    }
    fun getText(): TextView{
        return textViewHelper
    }
}