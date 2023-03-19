package id.co.ptn.hungrystock.ui.onboarding.fragments

import android.os.Bundle
import android.util.Log
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
import id.co.ptn.hungrystock.helper.extension.gone
import id.co.ptn.hungrystock.ui.onboarding.view_model.OnboardLatestEventViewModel
import id.co.ptn.hungrystock.ui.onboarding.view_model.OnboardViewModel
import id.co.ptn.hungrystock.ui.onboarding.view_model.WebinarViewModel
import id.co.ptn.hungrystock.utils.*
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
        binding?.card?.visibility = View.VISIBLE
        if (viewModel?.latestEvent?.image_file?.isNullOrEmpty() == true){
            binding?.image?.gone()
        } else {
            viewModel?.latestEvent?.image_file?.let { url ->
                MediaUtils(requireContext()).setImageFromUrl(binding?.image!!, url, R.drawable.img_event_placeholder)
            }
        }

        binding?.tvTitle?.text = viewModel?.latestEvent?.title ?: ""
        binding?.tvDescription?.text = viewModel?.latestEvent?.description ?: "-"
        binding?.tvDate?.text = "${getDateMMMMddyyyy((viewModel?.latestEvent?.date_from ?: 0) * 1000)}"
        binding?.tvDate?.append(" ${getHHmm((viewModel?.latestEvent?.date_from ?: 0) * 1000)} - ${getHHmm((viewModel?.latestEvent?.date_to ?: 0) * 1000)} WIB")
        binding?.tvSpeaker?.text = "Pembicara: ${viewModel?.latestEvent?.speakers ?: "-"}"
    }

    private fun setCodeOfConduct(title: String) {
        childFragmentManager.commit {
            val bundle = Bundle()
            bundle.putString("content", title)
            add<WebViewFragment>(R.id.frame_web, null, bundle)
        }
    }

    private fun setObserve() {
        viewModel?.reqOtpResponse()?.observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS -> {
                    binding?.progressBar?.visibility = View.GONE
                    if (running_service == RunningServiceType.EVENT){
                        viewModel?.apiGetLatestEvent(it.data?.data ?: "")
                    } else if (running_service == RunningServiceType.CODE_OF_CONDUCT) {
                        viewModel?.apiGetCodeOfConduct(it.data?.data ?: "")
                    }
                }
                Status.LOADING -> {
                    binding?.progressBar?.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding?.progressBar?.visibility = View.GONE
                }
            }
        }

        viewModel?.reqEventsResponse()?.observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS -> {
                    binding?.progressBar?.visibility = View.GONE
                    it.data?.data?.forEachIndexed { index, responseEventsData ->
                        if (index == 0){
                            viewModel?.latestEvent = responseEventsData
                            setView()
                            apiGetCodeOfConduct()
                            return@forEachIndexed
                        }
                    }
                }
                Status.LOADING -> {
                    binding?.progressBar?.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding?.progressBar?.visibility = View.GONE
                }
            }
        }

        viewModel?.reqCodeOfConductResponse()?.observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS -> {
                    binding?.progressBar?.visibility = View.GONE
                    it.data?.data?.forEach {data ->
                        setCodeOfConduct(data.title ?: "")
                    }
                }
                Status.LOADING -> {
                    binding?.progressBar?.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding?.progressBar?.visibility = View.GONE
                }
            }
        }
        onboardViewModel?.pageLatestEvent?.observe(requireActivity()){
            if (it){
                if (viewModel?.latestEvent == null) {
                    apiGetLatestEvent()
                }
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
    private fun apiGetCodeOfConduct() {
        running_service = RunningServiceType.CODE_OF_CONDUCT
        viewModel?.apiGetOtp()
    }

}