package id.co.ptn.hungrystock.ui.main.research

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.co.ptn.hungrystock.R

class ResearchFragment : Fragment() {

    companion object {
        fun newInstance() = ResearchFragment()
    }

    private lateinit var viewModel: ResearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.research_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ResearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

}