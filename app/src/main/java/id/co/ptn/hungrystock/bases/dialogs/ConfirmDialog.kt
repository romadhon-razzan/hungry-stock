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
    fun show(listener: Listener, positiveButton: String, negativeButton: String) {
        builder.setPositiveButton(positiveButton) { dialog, which ->
            listener.onPositiveClick()
        }

        builder.setNegativeButton(negativeButton) { dialog, which ->
            listener.onNegativeClick()
        }
        builder.show()
    }

    interface Listener {
        fun onPositiveClick()
        fun onNegativeClick()
    }
}