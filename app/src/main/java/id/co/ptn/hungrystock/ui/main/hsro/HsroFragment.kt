package id.co.ptn.hungrystock.ui.main.hsro

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.co.ptn.hungrystock.R

class HsroFragment : Fragment() {

    companion object {
        fun newInstance() = HsroFragment()
    }

    private lateinit var viewModel: HsroViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hsro_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HsroViewModel::class.java)
        // TODO: Use the ViewModel
    }

}