package id.co.ptn.hungrystock.bases.dialogs

import android.content.Context
import androidx.appcompat.app.AlertDialog

class InfoDialog(val context: Context) {
    private val builder = AlertDialog.Builder(context)
    private var listener: Listener? = null

    fun setTitle(title: String) {
        builder.setTitle(title)
    }
    fun setMessage(message: String) {
        builder.setMessage(message)
    }
    fun setListener(listener: Listener) {
        this.listener = listener
    }
    fun setCancelable(cancelable: Boolean) {
        builder.setCancelable(cancelable)
    }
    fun show(positiveButton: String) {
        builder.setPositiveButton(positiveButton) { dialog, which ->
            listener?.onPositiveClick()
        }

        builder.show()
    }

    interface Listener {
        fun onPositiveClick()
    }
}