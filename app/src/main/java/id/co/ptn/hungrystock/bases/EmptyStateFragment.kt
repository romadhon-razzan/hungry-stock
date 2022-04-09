package id.co.ptn.hungrystock.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.FragmentEmptyStateBinding

private const val ARG_TITLE = "title"
private const val ARG_MESSAGE = "message"

class EmptyStateFragment : Fragment() {
    private var title: String? = null
    private var message: String? = null

    private var binding: FragmentEmptyStateBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_TITLE)
            message = it.getString(ARG_MESSAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_empty_state, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
    }

    private fun setView() {
        title?.let { binding?.tvTitle?.text = it }
        message?.let { binding?.tvMessage?.text = it }
    }
}