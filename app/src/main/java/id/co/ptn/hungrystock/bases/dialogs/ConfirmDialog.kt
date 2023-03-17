package id.co.ptn.hungrystock.bases.dialogs

import android.content.Context
import androidx.appcompat.app.AlertDialog

class ConfirmDialog(val context: Context) {
    private val builder = AlertDialog.Builder(context)
//    var listener: Listener? = null

    fun setTitle(title: String) {
        builder.setTitle(title)
    }
    fun setMessage(message: String) {
        builder.setMessage(message)
    }
//    fun setListener(listener: Listener) {
//        this.listener = listener
//    }
    fun show(listener: Listener) {
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            listener.onPositiveClick()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            listener.onNegativeClick()
        }
        builder.show()
    }

    interface Listener {
        fun onPositiveClick()
        fun onNegativeClick()
    }
}