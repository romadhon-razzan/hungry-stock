package id.co.ptn.hungrystock.bases

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.FragmentWebViewBinding

private const val ARG_PARAM1 = "content"

class WebViewFragment : Fragment() {
    private var content: String? = null

    private var binding: FragmentWebViewBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            content = it.getString(ARG_PARAM1)
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
        content?.let {
            val desc = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<body style=\"color:grey;\">\n" +
                    "\n" +
                    it +
                    "\n" +
                    "</body>\n" +
                    "</html>"
            binding?.webView?.setBackgroundColor(Color.TRANSPARENT)
            binding?.webView?.loadData(desc, "text/html","UTF-8")
        }
    }
}