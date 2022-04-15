package id.co.ptn.hungrystock.bases.popup

import android.content.Context
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import id.co.ptn.hungrystock.R

class AbjadPopupMenu(private val context: Context, private val listener: Listener) {

    fun show(view: View) {
        val popup = PopupMenu(context, view)
        popup.inflate(R.menu.sorting_abjad_menu)

        popup.setOnMenuItemClickListener { item: MenuItem? ->
            item?.let {
                when (it.itemId) {
                    R.id.az -> {
                        listener.onSelected("A-Z")
                    }
                    R.id.za -> {
                        listener.onSelected("Z-A")
                    }
                }
            }
            true
        }

        popup.show()

    }

    public interface Listener{
        fun onSelected(abjad: String)
    }

}