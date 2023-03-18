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
import id.co.ptn.hungrystock.ui.onboarding.view_model.OnboardViewModel
import id.co.ptn.hungrystock.ui.onboarding.view_model.WebinarViewModel
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
    private var viewModel: WebinarViewModel? = null
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
        viewModel = ViewModelProvider(requireActivity())[WebinarViewModel::class.java]
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
        setView()
    }

    private fun setView() {

    }

    private fun setObserve() {
        viewModel?.reqOtpResponse()?.observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS -> {
                    if (running_service == RunningServiceType.WEBINAR){
                        viewModel?.apiGetWebinar(it.data?.data ?: "")
                    }
                }
                Status.LOADING -> {}
                Status.ERROR -> {}
            }
        }

        viewModel?.reqWebinarResponse()?.observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS -> {}
                Status.LOADING -> {}
                Status.ERROR -> {}
            }
        }
        onboardViewModel?.pageWebinar?.observe(requireActivity()){
            if (it){
                apiGetWebinar()
            }
        }
    }

    /**
     * Api
     * */
    private fun apiGetWebinar() {
        running_service = RunningServiceType.WEBINAR
        viewModel?.apiGetOtp()
    }

}