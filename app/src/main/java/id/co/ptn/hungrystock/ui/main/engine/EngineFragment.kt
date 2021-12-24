package id.co.ptn.hungrystock.ui.main.engine

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.co.ptn.hungrystock.R

class EngineFragment : Fragment() {

    companion object {
        fun newInstance() = EngineFragment()
    }

    private lateinit var viewModel: EngineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.engine_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EngineViewModel::class.java)
        // TODO: Use the ViewModel
    }

}