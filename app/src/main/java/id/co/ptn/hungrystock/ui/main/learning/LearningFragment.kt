package id.co.ptn.hungrystock.ui.main.learning

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.LearningFragmentBinding
import id.co.ptn.hungrystock.models.main.learning.Learning
import id.co.ptn.hungrystock.ui.main.learning.adapters.LearningListAdapter

@AndroidEntryPoint
class LearningFragment : Fragment() {

    companion object {
        fun newInstance() = LearningFragment()
    }

    private lateinit var binding: LearningFragmentBinding
    private val viewModel: LearningViewModel by viewModels()
    private lateinit var learningListAdapter: LearningListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.learning_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initData()
        initList()
    }

    private fun initList() {
        learningListAdapter = LearningListAdapter(viewModel.getLearnings())
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = learningListAdapter
        }
    }

    private fun initData() {
        viewModel.getLearnings().clear()
        viewModel.getLearnings().add(0, Learning("This is the item title with supposed a long line","1:02:05"))
        viewModel.getLearnings().add(1, Learning("This is the item title with supposed a long line","1:02:05"))
        viewModel.getLearnings().add(2, Learning("This is the item title with supposed a long line","1:02:05"))
        viewModel.getLearnings().add(3, Learning("This is the item title with supposed a long line","1:02:05"))
        viewModel.getLearnings().add(4, Learning("This is the item title with supposed a long line","1:02:05"))
        viewModel.getLearnings().add(5, Learning("This is the item title with supposed a long line","1:02:05"))
    }

}