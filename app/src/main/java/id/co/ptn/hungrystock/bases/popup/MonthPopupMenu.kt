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
                        listener.onSelected("Januari")
                    }
                    R.id.m2 -> {
                        listener.onSelected("Februari")
                    }
                    R.id.m3 -> {
                        listener.onSelected("Maret")
                    }
                    R.id.m4 -> {
                        listener.onSelected("April")
                    }
                    R.id.m5 -> {
                        listener.onSelected("Mei")
                    }
                    R.id.m6 -> {
                        listener.onSelected("Juni")
                    }
                    R.id.m7 -> {
                        listener.onSelected("Juli")
                    }
                    R.id.m8 -> {
                        listener.onSelected("Agustus")
                    }
                    R.id.m9 -> {
                        listener.onSelected("September")
                    }
                    R.id.m10 -> {
                        listener.onSelected("Oktober")
                    }
                    R.id.m11 -> {
                        listener.onSelected("November")
                    }
                    R.id.m12 -> {
                        listener.onSelected("Desember")
                    }
                }
            }
            true
        }

        popup.show()

    }

    public interface Listener{
        fun onSelected(month: String)
    }

}