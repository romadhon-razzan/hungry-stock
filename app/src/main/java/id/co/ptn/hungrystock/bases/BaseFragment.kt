package id.co.ptn.hungrystock.bases

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.techiness.progressdialoglibrary.ProgressDialog
import id.co.ptn.hungrystock.core.SessionManager
import id.co.ptn.hungrystock.router.Router
import id.co.ptn.hungrystock.ui.general.view_model.OtpViewModel

open class BaseFragment: Fragment() {
    lateinit var router: Router
    var sessionManager: SessionManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = SessionManager.getInstance(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        router = Router(requireActivity())
    }


    fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun View.showSnackBar(
        view: View,
        msg: String,
        length: Int,
        actionMessage: CharSequence?,
        action: (View) -> Unit
    ) {
        val snackBar = Snackbar.make(view, msg, length)
        if (actionMessage != null) {
            snackBar.setAction(actionMessage) {
                action(this)
            }.show()
        } else {
            snackBar.show()
        }
    }

    fun loading(view: View, loading: Boolean) {
        if (loading) view.visibility = View.VISIBLE
        else view.visibility = View.GONE
    }

    fun dialogLoading(): ProgressDialog {
        return ProgressDialog(requireContext())
    }

    fun Context.openAppSystemSettings() {
        startActivity(Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", packageName, null)
        })
    }

    fun openUrlPage(uri: String, context: Context) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        context.startActivity(browserIntent)
    }

}