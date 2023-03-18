package id.co.ptn.hungrystock.ui.onboarding.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseFragment
import id.co.ptn.hungrystock.core.network.RunningServiceType
import id.co.ptn.hungrystock.core.network.running_service
import id.co.ptn.hungrystock.databinding.FragmentPageFourBinding
import id.co.ptn.hungrystock.ui.onboarding.view_model.BooksViewModel
import id.co.ptn.hungrystock.ui.onboarding.view_model.OnboardViewModel
import id.co.ptn.hungrystock.utils.Status

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class PageFourFragment : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentPageFourBinding
    private var viewModel: BooksViewModel? = null
    private var onboardViewModel: OnboardViewModel? = null

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PageOneFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        viewModel = ViewModelProvider(requireActivity())[BooksViewModel::class.java]
        onboardViewModel = ViewModelProvider(requireActivity())[OnboardViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()),R.layout.fragment_page_four, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setObserve()
        setView()
    }

    private fun setView() {

    }

    private fun setObserve() {
        viewModel?.reqOtpResponse()?.observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS -> {
                    if (running_service == RunningServiceType.BOOKS){
                        viewModel?.apiGetBooks(it.data?.data ?: "")
                    }
                }
                Status.LOADING -> {}
                Status.ERROR -> {}
            }
        }
        viewModel?.reqBooksResponse()?.observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS -> {}
                Status.LOADING -> {}
                Status.ERROR -> {}
            }
        }
        onboardViewModel?.pageBooks?.observe(requireActivity()){
            if (it){
                apiGetBooks()
            }
        }
    }

    /**
     * Api
     * */
    private fun apiGetBooks() {
        running_service = RunningServiceType.BOOKS
        viewModel?.apiGetOtp()
    }
}