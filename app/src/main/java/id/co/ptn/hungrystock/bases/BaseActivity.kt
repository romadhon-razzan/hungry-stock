package id.co.ptn.hungrystock.bases

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import id.co.ptn.hungrystock.core.SessionManager

open class BaseActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = SessionManager.getInstance(this)
    }

    fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun loading(view: View, loading: Boolean) {
        if (loading) view.visibility = View.VISIBLE
        else view.visibility = View.GONE
    }
}