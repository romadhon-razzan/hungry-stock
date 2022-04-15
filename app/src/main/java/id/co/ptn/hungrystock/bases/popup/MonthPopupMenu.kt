package id.co.ptn.hungrystock.bases.popup

import android.content.Context
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import id.co.ptn.hungrystock.R

class MonthPopupMenu(private val context: Context, private val listener: Listener) {

    fun show(view: View) {
        val popup = PopupMenu(context, view)
        popup.inflate(R.menu.monts_menu)

        popup.setOnMenuItemClickListener { item: MenuItem? ->
            item?.let {
                when (it.itemId) {
                    R.id.m1 -> {
                        listener.onSelected("Januari","01")
                    }
                    R.id.m2 -> {
                        listener.onSelected("Februari","02")
                    }
                    R.id.m3 -> {
                        listener.onSelected("Maret","03")
                    }
                    R.id.m4 -> {
                        listener.onSelected("April","04")
                    }
                    R.id.m5 -> {
                        listener.onSelected("Mei","05")
                    }
                    R.id.m6 -> {
                        listener.onSelected("Juni","06")
                    }
                    R.id.m7 -> {
                        listener.onSelected("Juli","07")
                    }
                    R.id.m8 -> {
                        listener.onSelected("Agustus","08")
                    }
                    R.id.m9 -> {
                        listener.onSelected("September","09")
                    }
                    R.id.m10 -> {
                        listener.onSelected("Oktober","10")
                    }
                    R.id.m11 -> {
                        listener.onSelected("November","11")
                    }
                    R.id.m12 -> {
                        listener.onSelected("Desember","12")
                    }
                }
            }
            true
        }

        popup.show()

    }

    public interface Listener{
        fun onSelected(month: String, id: String)
    }

}