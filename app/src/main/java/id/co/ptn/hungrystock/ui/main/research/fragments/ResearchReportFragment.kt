package id.co.ptn.hungrystock.ui.main.research.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.FragmentResearchReportBinding
import id.co.ptn.hungrystock.databinding.ResearchFragmentBinding
import id.co.ptn.hungrystock.ui.main.research.viewmodel.ResearchReportViewModel
import id.co.ptn.hungrystock.ui.main.research.viewmodel.ResearchViewModel

@AndroidEntryPoint
class ResearchReportFragment : Fragment() {

    companion object {
        fun newInstance() = ResearchReportFragment()
    }

    private lateinit var binding: FragmentResearchReportBinding
    private val viewModel: ResearchReportViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_research_report, container, false)
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initList()
    }

    private fun initList() {

    }
}