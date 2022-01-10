package id.co.ptn.hungrystock.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import id.co.ptn.hungrystock.router.Router

open class BaseFragment: Fragment() {
    lateinit var router: Router
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

    fun loading(view: View, loading: Boolean) {
        if (loading) view.visibility = View.VISIBLE
        else view.visibility = View.GONE
    }

}