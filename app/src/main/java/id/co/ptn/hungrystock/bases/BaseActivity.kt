package id.co.ptn.hungrystock.bases

import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import id.co.ptn.hungrystock.core.SessionManager
import id.co.ptn.hungrystock.router.Router
import androidx.core.view.WindowInsetsControllerCompat
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.helper.Keyboard


open class BaseActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager
    lateinit var router: Router
    lateinit var keyboard: Keyboard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = SessionManager.getInstance(this)
        router = Router(this)
    }

    fun changeStatusBar() {
        val window: Window = window
        val decorView: View = window.decorView
        val wic = WindowInsetsControllerCompat(window, decorView)
        wic.isAppearanceLightStatusBars = true
        window.statusBarColor = ContextCompat.getColor(this, R.color.background)
    }

    fun setToolbar(toolbar: Toolbar, title: String?) {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(true)
            if (title != null) it.title =
                title else it.setDisplayShowTitleEnabled(false)
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_back)
            toolbar.setNavigationOnClickListener { onBackPressed() }
        }

    }

    fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun loading(view: View, loading: Boolean) {
        if (loading) view.visibility = View.VISIBLE
        else view.visibility = View.GONE
    }
}