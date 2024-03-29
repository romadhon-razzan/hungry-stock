package id.co.ptn.hungrystock.bases

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.FragmentWebViewBinding

private const val ARG_PARAM1 = "content"
private const val ARG_FONT_SIZE = "font_size"

class WebViewFragment : Fragment() {
    private var content: String? = null
    private var fontSize: String? = null

    private var binding: FragmentWebViewBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            content = it.getString(ARG_PARAM1)
            fontSize = it.getString(ARG_FONT_SIZE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_web_view, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
    }

    private fun setView() {
        var fSize = "font-size: 12px;"
        when(fontSize) {
            "small" -> {fSize = "font-size: 10px;"}
            "normal" -> {fSize = "font-size: 14px;"}
        }

        content?.let {
            val desc = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<body style=\"${fSize}color:grey;\">\n" +
                    "\n" +
                    it +
                    "\n" +
                    "</body>\n" +
                    "</html>"
            binding?.webView?.setBackgroundColor(Color.TRANSPARENT)

            binding?.webView?.webViewClient = WebViewClient()
            binding?.webView?.loadData(desc, "text/html","UTF-8")
        }
    }

    inner class WebViewClient : android.webkit.WebViewClient() {

        // Load the URL
        @Deprecated("Deprecated in Java")
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return false
        }

        // ProgressBar will disappear once page is loaded
        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            binding?.progressBar?.visibility = View.GONE
        }
    }

}