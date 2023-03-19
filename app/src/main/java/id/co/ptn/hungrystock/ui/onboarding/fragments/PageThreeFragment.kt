package id.co.ptn.hungrystock.ui.onboarding.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseFragment
import id.co.ptn.hungrystock.bases.WebViewFragment
import id.co.ptn.hungrystock.core.network.RunningServiceType
import id.co.ptn.hungrystock.core.network.running_service
import id.co.ptn.hungrystock.databinding.FragmentPageThreeBinding
import id.co.ptn.hungrystock.ui.onboarding.view_model.OnboardLatestEventViewModel
import id.co.ptn.hungrystock.ui.onboarding.view_model.OnboardViewModel
import id.co.ptn.hungrystock.ui.onboarding.view_model.WebinarViewModel
import id.co.ptn.hungrystock.utils.MediaUtils
import id.co.ptn.hungrystock.utils.Status
import id.co.ptn.hungrystock.utils.getDateMMMMddyyyy
import java.lang.Exception
import java.lang.StringBuilder

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class PageThreeFragment : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var binding: FragmentPageThreeBinding? = null
    private var viewModel: OnboardLatestEventViewModel? = null
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
        viewModel = ViewModelProvider(requireActivity())[OnboardLatestEventViewModel::class.java]
        onboardViewModel = ViewModelProvider(requireActivity())[OnboardViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_page_three, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = this
        setObserve()
    }

    private fun setView() {
        MediaUtils(requireContext()).setImageFromUrl(binding?.image!!, viewModel?.latestEvent?.image_file ?: "")
        binding?.tvTitle?.text = viewModel?.latestEvent?.title ?: ""
        binding?.tvDescription?.text = viewModel?.latestEvent?.description ?: ""
        binding?.tvSpeaker?.text = "Pembicara: ${viewModel?.latestEvent?.speakers ?: ""}"
    }

    private fun setObserve() {
        viewModel?.reqOtpResponse()?.observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS -> {
                    if (running_service == RunningServiceType.EVENT){
                        viewModel?.apiGetLatestEvent(it.data?.data ?: "")
                    }
                }
                Status.LOADING -> {}
                Status.ERROR -> {}
            }
        }

        viewModel?.reqEventsResponse()?.observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS -> {
                    it.data?.data?.forEachIndexed { index, responseEventsData ->
                        if (index == 0){
                            viewModel?.latestEvent = responseEventsData
                            setView()
                            return@forEachIndexed
                        }
                    }
                }
                Status.LOADING -> {}
                Status.ERROR -> {}
            }
        }
        onboardViewModel?.pageLatestEvent?.observe(requireActivity()){
            if (it){
                apiGetLatestEvent()
            }
        }
    }

    /**
     * Api
     * */
    private fun apiGetLatestEvent() {
        running_service = RunningServiceType.EVENT
        viewModel?.apiGetOtp()
    }

}